/*
 * Copyright (C) 2011 Jan Pokorsky
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.incad.pas.editor.server.rest;

import cz.incad.pas.editor.server.imports.ImportBatchManager;
import cz.incad.pas.editor.server.imports.ImportBatchManager.ImportBatch;
import cz.incad.pas.editor.server.imports.ImportBatchManager.ImportItem;
import cz.incad.pas.editor.server.imports.ImportFileScanner;
import cz.incad.pas.editor.server.imports.ImportFileScanner.Folder;
import cz.incad.pas.editor.server.imports.ImportFileScanner.State;
import cz.incad.pas.editor.server.imports.ImportProcess;
import cz.incad.pas.editor.server.imports.ImportProcess.ImportItemFailure;
import cz.incad.pas.editor.server.user.UserManager;
import cz.incad.pas.editor.server.user.UserProfile;
import cz.incad.pas.editor.server.user.UserUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

/**
 * REST resource to retrieve import folders.
 *
 * TODO /import/ GET - lists subfolders; POST - import folder; DELETE - delete folder
 *      /import/batch/ GET - lists imported folders; POST - import folder
 *      /import/object/ GET - lists imported objects; POST - import folder
 * TODO /object/{pid}/ GET - read DigObjDesc:{pid, displayname, date, owner};
 *      /object/ GET - lists all DigObjDesc
 *      /object/{pid}/foxml
 *      /object/{pid}/scan
 *      /object/{pid}/preview
 *      /object/{pid}/thumb
 *      /object/{pid}/ocr
 *      /object/{pid}/metadata
 *      /object/{pid}/relations
 *      /object/metamodel/ GET - lists model:{pid, displayname, type:(TOP|LEAF)}
 *      /user/ GET list User:{id, uname, displayName}, POST - new user, DELETE - delete user, PUT - update user
 *      /user/{id}/profile GET profile:{importFolder, roles:[IMPORTER|ADMIN|...]}, PUT - update
 * 
 * @author Jan Pokorsky
 * @see <a href="http://127.0.0.1:8888/Editor/rest/import">test in dev mode</a>
 * @see <a href="http://127.0.0.1:8888/Editor/rest/application.wadl">WADL in dev mode</a>
 * @see <a href="http://127.0.0.1:8888/Editor/rest/application.wadl/xsd0.xsd">XML Scema in dev mode</a>
 */
@Path("/import")
public class ImportResource {

    private static final Logger LOG = Logger.getLogger(ImportResource.class.getName());
    private static final Pattern INVALID_PATH_CONTENT = Pattern.compile("\\.\\.|//");

    // XXX inject with guice
    private final UserManager userManager;

    private final SecurityContext securityCtx;
    private final UserProfile user;
    private final HttpHeaders httpHeaders;
    private final UriInfo uriInfo;

    public ImportResource(
            @Context SecurityContext securityCtx,
            @Context HttpHeaders httpHeaders,
            @Context UriInfo uriInfo
            /*UserManager userManager*/
            ) {

        UserManager userManager = UserUtil.createUserManagerMemoryImpl(); // XXX replace with injection
        this.userManager = userManager;
        this.securityCtx = securityCtx;
        Principal userPrincipal = securityCtx.getUserPrincipal();
        System.out.println("## userPrincipal: " + userPrincipal);
        String userName;
        if (userPrincipal != null) {
            userName = userPrincipal.getName();
            System.out.println("## userPrincipalName: " + userName);
            boolean userInRole = securityCtx.isUserInRole("Importer");
            System.out.println("## user.isImporter: " + userInRole);
        } else {
            userName = "datel";
        }
        user = userManager.find(userName);
        // when not found throw exception not logged?
        LOG.info(user.toString());

        this.httpHeaders = httpHeaders;
        LOG.info(String.format("HttpHeaders[\n lang: %s, type: %s"
                    + "\n acceptableLang: %s\n acceptableMediaTypes: %s\n headers: %s]",
                httpHeaders.getLanguage(),
                httpHeaders.getMediaType(),
                httpHeaders.getAcceptableLanguages(),
                httpHeaders.getAcceptableMediaTypes(),
                httpHeaders.getRequestHeaders()));

        this.uriInfo = uriInfo;
        LOG.info(String.format("UriInfo.getQueryParameters: %s", uriInfo.getQueryParameters()));
    }

    /**
     * Lists subfolders and their import states.
     *
     * @param parent folder path relative to user's import folder
     * @return folder contents (path without initial slash and always terminated with slash: A/, A/B/)
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SmartGwtResponse list(
            @QueryParam("parent") @DefaultValue("") String parent
            ) throws FileNotFoundException, URISyntaxException {

        String parentPath = validateParentPath(parent);

        URI userRoot = user.getImportFolder();
        URI path = (parentPath != null)
                // URI multi param constructor escapes input unlike single param constructor or URI.create!
                ? userRoot.resolve(new URI(null, null, parentPath, null))
                : userRoot;
        LOG.log(Level.INFO, "parent: {0} used as {1} resolved to {2}", new Object[] {parent, parentPath, path});

        ImportFileScanner scanner = new ImportFileScanner();
        List<Folder> subfolders = scanner.findSubfolders(new File(path));
        List<ImportFolder> result = new ArrayList<ImportFolder>(subfolders.size());
        for (Folder subfolder : subfolders) {
            String subfolderName = subfolder.getHandle().getName();
            String subfolderStatus = subfolder.getStatus().name();
            String subfolderPath = userRoot.relativize(subfolder.getHandle().toURI()).getPath();
            result.add(new ImportFolder(subfolderName, subfolderStatus, parentPath, subfolderPath));
        }

        System.out.println("list: " + result);
        return new SmartGwtResponse(SmartGwtResponse.STATUS_SUCCESS, null, null, null, result);
    }

    private ImportFolder create(File folder, ImportFileScanner.State state, URI userRoot) {
        String name = folder.getName();
        String path = userRoot.relativize(folder.toURI()).getPath();
        String parentPath = userRoot.relativize(folder.getParentFile().toURI()).getPath();
        return new ImportFolder(name, state.name(), parentPath, path);
    }

    @POST
    @Path("batch")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ImportBatch importFolder(
            @FormParam("path") @DefaultValue("") String path,
            @FormParam("model") @DefaultValue("model:path") String model
            ) throws URISyntaxException, IOException {
        
        LOG.log(Level.INFO, "import path: {0} as model: {1}", new Object[] {path, model});
        String folderPath = validateParentPath(path);
        URI userRoot = user.getImportFolder();
        URI folderUri = (folderPath != null)
                // URI multi param constructor escapes input unlike single param constructor or URI.create!
                ? userRoot.resolve(new URI(null, null, folderPath, null))
                : userRoot;
        File folder = new File(folderUri);
        // XXX do import
        ImportProcess process = new ImportProcess(folder, folderPath, user.getId(), ImportBatchManager.getInstance());
        ImportBatch batch = process.start();
//        List<ImportItem> importedItems = process.getImportedItems();
//        List<ImportItemFailure> failures = process.getFailures();
//        importedItems.get(0).getFilename();
//        failures.get(0).getFilename();

        return batch;

//        List<ImportFolder> result = Collections.singletonList(create(folder, State.IMPORTED, userRoot));
//        return new SmartGwtResponse(RPCResponse.STATUS_SUCCESS, null, null, null, result);
    }

    public static class ImportFolderResult {
        
    }

    @GET
    @Path("batch")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ImportBatchList listBatches() {
        ImportBatchManager ibmanager = ImportBatchManager.getInstance();
        return new ImportBatchList(ibmanager.findAll(user.getId(), false));
    }

    @XmlRootElement(name="batches")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ImportBatchList {

        @XmlElement(name="batch")
        private Collection<ImportBatch> batches;

        public ImportBatchList() {}

        public ImportBatchList(Collection<ImportBatch> batches) {
            this.batches = batches;
        }

    }
    
    @GET
    @Path("object")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    // XXX listImportedObjects needs paging support
    public ImportedDigitalObjectList listImportedObjects(
            @QueryParam("batchId") Integer batchId
            ) {
        return new ImportedDigitalObjectList(Collections.<ImportedDigitalObject>emptyList());
    }

    @XmlRootElement(name="records")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ImportedDigitalObjectList {

        @XmlElement(name="record")
        private Collection<ImportedDigitalObject> records;

        public ImportedDigitalObjectList() {}

        public ImportedDigitalObjectList(Collection<ImportedDigitalObject> objects) {
            this.records = objects;
        }

    }

    public static class ImportedDigitalObject {
        private int id;
        private int batchId;
        private Object pid;
        private String filename;
        private String model;

        private int userId;
        private String userDisplayName;
    }

    private static String validateParentPath(String parent) {
        if (parent == null || parent.length() == 0) {
            return null;
        }

        if ("null".equals(parent)) { // XXX fix parent param on client side
            return null;
        }

        // stop on dangerous chars; it could introduce vulnerability
        if (INVALID_PATH_CONTENT.matcher(parent).find()) {
            throw new IllegalArgumentException("Invalid 'parent' param! " + parent);
        }

        // parent must not be absolute path
        if (parent.charAt(0) == '/') {
            parent = (parent.length() == 1) ? null : parent.substring(1);
        }
        return parent;

    }

//    final static Map<String, List<ImportFolder>> MAP = new HashMap<String, List<ImportFolder>>();
//    static {
//        List<ImportFolder> data = Arrays.asList(
//                new ImportFolder("2010", "IMPORTED", null, "2010/"),
//                new ImportFolder("2011", "NEW", null, "2011/"),
//                new ImportFolder("02", null, "2011/", "2011/02/"),
//                new ImportFolder("28", "IMPORTED", "2011/02/", "2011/02/28/"),
//                new ImportFolder("ABA0070069228X", "RUNNING", "2011/02/28/", "2011/02/28/ABA0070069228X/"),
//                new ImportFolder("ABA0070069228X2", "IMPORTED", "2011/02/28/", "2011/02/28/ABA0070069228X2/"),
//                new ImportFolder("29", null, "2011/02/", "2011/02/29/")
//                );
//        for (ImportFolder item : data) {
//            String parent = item.getParent();
//            parent = String.valueOf(parent);
//            List<ImportFolder> list = MAP.get(parent);
//            if (list == null) {
//                list = new ArrayList<ImportFolder>();
//                MAP.put(parent, list);
//            }
//            list.add(item);
//        }
//        System.out.println("map: " + MAP);
//    }
}