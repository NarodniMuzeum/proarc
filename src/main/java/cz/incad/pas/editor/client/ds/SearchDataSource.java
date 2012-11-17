/*
 * Copyright (C) 2012 Jan Pokorsky
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
package cz.incad.pas.editor.client.ds;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import cz.incad.pas.editor.shared.rest.DigitalObjectResourceApi;
import cz.incad.pas.editor.shared.rest.DigitalObjectResourceApi.SearchType;
import java.util.HashMap;

/**
 *
 * @author Jan Pokorsky
 */
public final class SearchDataSource extends RestDataSource {

    public static final String ID = "SearchDataSource";

    public static final String FIELD_PID = DigitalObjectResourceApi.MEMBERS_ITEM_PID;
    public static final String FIELD_MODEL = DigitalObjectResourceApi.MEMBERS_ITEM_MODEL;
    public static final String FIELD_OWNER = DigitalObjectResourceApi.MEMBERS_ITEM_OWNER;
    public static final String FIELD_LABEL = DigitalObjectResourceApi.MEMBERS_ITEM_LABEL;
    public static final String FIELD_STATE = DigitalObjectResourceApi.MEMBERS_ITEM_STATE;
    public static final String FIELD_CREATED = DigitalObjectResourceApi.MEMBERS_ITEM_CREATED;
    public static final String FIELD_MODIFIED = DigitalObjectResourceApi.MEMBERS_ITEM_MODIFIED;

    public SearchDataSource() {
        setID(ID);
        setDataFormat(DSDataFormat.JSON);
        setDataURL(RestConfig.URL_DIGOBJECT_SEARCH);

        DataSourceField pid = new DataSourceField(FIELD_PID, FieldType.TEXT);
        pid.setPrimaryKey(true);

        DataSourceField owner = new DataSourceField(FIELD_OWNER, FieldType.TEXT);
        DataSourceField label = new DataSourceField(FIELD_LABEL, FieldType.TEXT);
        DataSourceField state = new DataSourceField(FIELD_STATE, FieldType.ENUM);
        HashMap<String, String> states = new HashMap<String, String>();
        states.put("fedora-system:def/model#Active", "Active");
        states.put("fedora-system:def/model#Inactive", "Inactive");
        states.put("fedora-system:def/model#Deleted", "Deleted");
        state.setValueMap(states);
        DataSourceDateTimeField created = new DataSourceDateTimeField(FIELD_CREATED);
        created.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
        DataSourceDateTimeField modified = new DataSourceDateTimeField(FIELD_MODIFIED);
        modified.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

        DataSourceTextField model = new DataSourceTextField(FIELD_MODEL);
        model.setForeignKey(MetaModelDataSource.ID + '.' + MetaModelDataSource.FIELD_PID);

        setFields(label, model, pid, created, modified, owner, state);
        setRequestProperties(RestConfig.createRestRequest(getDataFormat()));
    }

    public static SearchDataSource getInstance() {
        SearchDataSource ds = (SearchDataSource) DataSource.get(ID);
        ds = (ds != null) ? ds : new SearchDataSource();
        return ds;
    }

    public RecordList find(String pid, final BooleanCallback callback) {
        final RecordList rs = new RecordList();
        Criteria criteria = new Criteria(
                DigitalObjectResourceApi.SEARCH_TYPE_PARAM, SearchType.PIDS.toString());
        if (pid != null && !pid.isEmpty()) {
            criteria.addCriteria(DigitalObjectResourceApi.SEARCH_PID_PARAM, pid);
        } else {
            throw new IllegalArgumentException("pid");
        }
        fetchData(criteria, new DSCallback() {

            @Override
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                if (RestConfig.isStatusOk(response)) {
                    rs.addList(response.getData());
                    callback.execute(Boolean.TRUE);
                } else {
                    callback.execute(Boolean.FALSE);
                }
            }
        });
        return rs;
    }

    public static boolean isDeleted(Record r) {
        return "fedora-system:def/model#Deleted".equals(r.getAttribute(FIELD_STATE));
    }

}
