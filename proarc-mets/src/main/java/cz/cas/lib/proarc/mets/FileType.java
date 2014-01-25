/*
 * Copyright (C) 2014 Robert Simonovsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package cz.cas.lib.proarc.mets;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import org.w3c.dom.Element;

/**
 * fileType: Complex Type for Files The file element provides access to content
 * files for a METS object. A file element may contain one or more FLocat
 * elements, which provide pointers to a content file, and/or an FContent
 * element, which wraps an encoded version of the file. Note that ALL FLocat and
 * FContent elements underneath a single file element should identify/contain
 * identical copies of a single file.
 * 
 * 
 * <p>
 * Java class for fileType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="fileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FLocat" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/>
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}LOCATION"/>
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                 &lt;attribute name="USE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="FContent" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="binData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                   &lt;element name="xmlData" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;any processContents='lax' maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                 &lt;attribute name="USE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="stream" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                 &lt;attribute name="streamType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="OWNERID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *                 &lt;attribute name="DMDID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *                 &lt;attribute name="BEGIN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="END" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="BETYPE">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="BYTE"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="transformFile" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                 &lt;attribute name="TRANSFORMTYPE" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="decompression"/>
 *                       &lt;enumeration value="decryption"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="TRANSFORMALGORITHM" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="TRANSFORMKEY" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="TRANSFORMBEHAVIOR" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *                 &lt;attribute name="TRANSFORMORDER" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="file" type="{http://www.loc.gov/METS/}fileType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.loc.gov/METS/}FILECORE"/>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="SEQ" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="OWNERID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *       &lt;attribute name="DMDID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *       &lt;attribute name="GROUPID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="USE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BEGIN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="END" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BETYPE">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="BYTE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileType", namespace = "http://www.loc.gov/METS/", propOrder = { "fLocat", "fContent", "stream", "transformFile", "file" })
public class FileType {

    @XmlElement(name = "FLocat", namespace = "http://www.loc.gov/METS/")
    protected List<FileType.FLocat> fLocat;
    @XmlElement(name = "FContent", namespace = "http://www.loc.gov/METS/")
    protected FileType.FContent fContent;
    @XmlElement(namespace = "http://www.loc.gov/METS/")
    protected List<FileType.Stream> stream;
    @XmlElement(namespace = "http://www.loc.gov/METS/")
    protected List<FileType.TransformFile> transformFile;
    @XmlElement(namespace = "http://www.loc.gov/METS/")
    protected List<FileType> file;
    @XmlAttribute(name = "ID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "SEQ")
    protected Integer seq;
    @XmlAttribute(name = "OWNERID")
    protected String ownerid;
    @XmlAttribute(name = "ADMID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> admid;
    @XmlAttribute(name = "DMDID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> dmdid;
    @XmlAttribute(name = "GROUPID")
    protected String groupid;
    @XmlAttribute(name = "USE")
    protected String use;
    @XmlAttribute(name = "BEGIN")
    protected String begin;
    @XmlAttribute(name = "END")
    protected String end;
    @XmlAttribute(name = "BETYPE")
    protected String betype;
    @XmlAttribute(name = "MIMETYPE")
    protected String mimetype;
    @XmlAttribute(name = "SIZE")
    protected Long size;
    @XmlAttribute(name = "CREATED")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar created;
    @XmlAttribute(name = "CHECKSUM")
    protected String checksum;
    @XmlAttribute(name = "CHECKSUMTYPE")
    protected String checksumtype;

    /**
     * Gets the value of the fLocat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the fLocat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFLocat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileType.FLocat }
     * 
     * 
     */
    public List<FileType.FLocat> getFLocat() {
        if (fLocat == null) {
            fLocat = new ArrayList<FileType.FLocat>();
        }
        return this.fLocat;
    }

    /**
     * Gets the value of the fContent property.
     * 
     * @return possible object is {@link FileType.FContent }
     * 
     */
    public FileType.FContent getFContent() {
        return fContent;
    }

    /**
     * Sets the value of the fContent property.
     * 
     * @param value
     *            allowed object is {@link FileType.FContent }
     * 
     */
    public void setFContent(FileType.FContent value) {
        this.fContent = value;
    }

    /**
     * Gets the value of the stream property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the stream property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStream().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileType.Stream }
     * 
     * 
     */
    public List<FileType.Stream> getStream() {
        if (stream == null) {
            stream = new ArrayList<FileType.Stream>();
        }
        return this.stream;
    }

    /**
     * Gets the value of the transformFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the transformFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTransformFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileType.TransformFile }
     * 
     * 
     */
    public List<FileType.TransformFile> getTransformFile() {
        if (transformFile == null) {
            transformFile = new ArrayList<FileType.TransformFile>();
        }
        return this.transformFile;
    }

    /**
     * Gets the value of the file property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the file property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FileType }
     * 
     * 
     */
    public List<FileType> getFile() {
        if (file == null) {
            file = new ArrayList<FileType>();
        }
        return this.file;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the seq property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getSEQ() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setSEQ(Integer value) {
        this.seq = value;
    }

    /**
     * Gets the value of the ownerid property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOWNERID() {
        return ownerid;
    }

    /**
     * Sets the value of the ownerid property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOWNERID(String value) {
        this.ownerid = value;
    }

    /**
     * Gets the value of the admid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the admid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getADMID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Object }
     * 
     * 
     */
    public List<Object> getADMID() {
        if (admid == null) {
            admid = new ArrayList<Object>();
        }
        return this.admid;
    }

    /**
     * Gets the value of the dmdid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dmdid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDMDID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Object }
     * 
     * 
     */
    public List<Object> getDMDID() {
        if (dmdid == null) {
            dmdid = new ArrayList<Object>();
        }
        return this.dmdid;
    }

    /**
     * Gets the value of the groupid property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGROUPID() {
        return groupid;
    }

    /**
     * Sets the value of the groupid property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGROUPID(String value) {
        this.groupid = value;
    }

    /**
     * Gets the value of the use property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUSE() {
        return use;
    }

    /**
     * Sets the value of the use property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUSE(String value) {
        this.use = value;
    }

    /**
     * Gets the value of the begin property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBEGIN() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBEGIN(String value) {
        this.begin = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEND() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEND(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the betype property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBETYPE() {
        return betype;
    }

    /**
     * Sets the value of the betype property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBETYPE(String value) {
        this.betype = value;
    }

    /**
     * Gets the value of the mimetype property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMIMETYPE() {
        return mimetype;
    }

    /**
     * Sets the value of the mimetype property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMIMETYPE(String value) {
        this.mimetype = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getSIZE() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *            allowed object is {@link Long }
     * 
     */
    public void setSIZE(Long value) {
        this.size = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getCREATED() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setCREATED(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Gets the value of the checksum property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCHECKSUM() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCHECKSUM(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the checksumtype property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCHECKSUMTYPE() {
        return checksumtype;
    }

    /**
     * Sets the value of the checksumtype property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCHECKSUMTYPE(String value) {
        this.checksumtype = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="binData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
     *         &lt;element name="xmlData" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;any processContents='lax' maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/choice>
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *       &lt;attribute name="USE" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "binData", "xmlData" })
    public static class FContent {

        @XmlElement(namespace = "http://www.loc.gov/METS/")
        protected byte[] binData;
        @XmlElement(namespace = "http://www.loc.gov/METS/")
        protected FileType.FContent.XmlData xmlData;
        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "USE")
        protected String use;

        /**
         * Gets the value of the binData property.
         * 
         * @return possible object is byte[]
         */
        public byte[] getBinData() {
            return binData;
        }

        /**
         * Sets the value of the binData property.
         * 
         * @param value
         *            allowed object is byte[]
         */
        public void setBinData(byte[] value) {
            this.binData = value;
        }

        /**
         * Gets the value of the xmlData property.
         * 
         * @return possible object is {@link FileType.FContent.XmlData }
         * 
         */
        public FileType.FContent.XmlData getXmlData() {
            return xmlData;
        }

        /**
         * Sets the value of the xmlData property.
         * 
         * @param value
         *            allowed object is {@link FileType.FContent.XmlData }
         * 
         */
        public void setXmlData(FileType.FContent.XmlData value) {
            this.xmlData = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the use property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getUSE() {
            return use;
        }

        /**
         * Sets the value of the use property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setUSE(String value) {
            this.use = value;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         * 
         * <p>
         * The following schema fragment specifies the expected content
         * contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;any processContents='lax' maxOccurs="unbounded"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "any" })
        public static class XmlData {

            @XmlAnyElement(lax = true)
            protected List<Object> any;

            /**
             * Gets the value of the any property.
             * 
             * <p>
             * This accessor method returns a reference to the live list, not a
             * snapshot. Therefore any modification you make to the returned
             * list will be present inside the JAXB object. This is why there is
             * not a <CODE>set</CODE> method for the any property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * 
             * <pre>
             * getAny().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Object } {@link Element }
             * 
             * 
             */
            public List<Object> getAny() {
                if (any == null) {
                    any = new ArrayList<Object>();
                }
                return this.any;
            }

        }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/>
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}LOCATION"/>
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *       &lt;attribute name="USE" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FLocat {

        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "USE")
        protected String use;
        @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
        protected String type;
        @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink")
        @XmlSchemaType(name = "anyURI")
        protected String href;
        @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
        protected String role;
        @XmlAttribute(name = "arcrole", namespace = "http://www.w3.org/1999/xlink")
        protected String arcrole;
        @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
        protected String title;
        @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
        protected String show;
        @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
        protected String actuate;
        @XmlAttribute(name = "LOCTYPE", required = true)
        protected String loctype;
        @XmlAttribute(name = "OTHERLOCTYPE")
        protected String otherloctype;

        /**
         * Gets the value of the id property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the use property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getUSE() {
            return use;
        }

        /**
         * Sets the value of the use property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setUSE(String value) {
            this.use = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getType() {
            if (type == null) {
                return "simple";
            } else {
                return type;
            }
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the href property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getHref() {
            return href;
        }

        /**
         * Sets the value of the href property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setHref(String value) {
            this.href = value;
        }

        /**
         * Gets the value of the role property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getRole() {
            return role;
        }

        /**
         * Sets the value of the role property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setRole(String value) {
            this.role = value;
        }

        /**
         * Gets the value of the arcrole property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getArcrole() {
            return arcrole;
        }

        /**
         * Sets the value of the arcrole property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setArcrole(String value) {
            this.arcrole = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Gets the value of the show property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getShow() {
            return show;
        }

        /**
         * Sets the value of the show property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setShow(String value) {
            this.show = value;
        }

        /**
         * Gets the value of the actuate property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getActuate() {
            return actuate;
        }

        /**
         * Sets the value of the actuate property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setActuate(String value) {
            this.actuate = value;
        }

        /**
         * Gets the value of the loctype property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getLOCTYPE() {
            return loctype;
        }

        /**
         * Sets the value of the loctype property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setLOCTYPE(String value) {
            this.loctype = value;
        }

        /**
         * Gets the value of the otherloctype property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getOTHERLOCTYPE() {
            return otherloctype;
        }

        /**
         * Sets the value of the otherloctype property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setOTHERLOCTYPE(String value) {
            this.otherloctype = value;
        }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *       &lt;attribute name="streamType" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="OWNERID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
     *       &lt;attribute name="DMDID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
     *       &lt;attribute name="BEGIN" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="END" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="BETYPE">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="BYTE"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Stream {

        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "streamType")
        protected String streamType;
        @XmlAttribute(name = "OWNERID")
        protected String ownerid;
        @XmlAttribute(name = "ADMID")
        @XmlIDREF
        @XmlSchemaType(name = "IDREFS")
        protected List<Object> admid;
        @XmlAttribute(name = "DMDID")
        @XmlIDREF
        @XmlSchemaType(name = "IDREFS")
        protected List<Object> dmdid;
        @XmlAttribute(name = "BEGIN")
        protected String begin;
        @XmlAttribute(name = "END")
        protected String end;
        @XmlAttribute(name = "BETYPE")
        protected String betype;

        /**
         * Gets the value of the id property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the streamType property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getStreamType() {
            return streamType;
        }

        /**
         * Sets the value of the streamType property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setStreamType(String value) {
            this.streamType = value;
        }

        /**
         * Gets the value of the ownerid property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getOWNERID() {
            return ownerid;
        }

        /**
         * Sets the value of the ownerid property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setOWNERID(String value) {
            this.ownerid = value;
        }

        /**
         * Gets the value of the admid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the admid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getADMID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getADMID() {
            if (admid == null) {
                admid = new ArrayList<Object>();
            }
            return this.admid;
        }

        /**
         * Gets the value of the dmdid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the dmdid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getDMDID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getDMDID() {
            if (dmdid == null) {
                dmdid = new ArrayList<Object>();
            }
            return this.dmdid;
        }

        /**
         * Gets the value of the begin property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getBEGIN() {
            return begin;
        }

        /**
         * Sets the value of the begin property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setBEGIN(String value) {
            this.begin = value;
        }

        /**
         * Gets the value of the end property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getEND() {
            return end;
        }

        /**
         * Sets the value of the end property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setEND(String value) {
            this.end = value;
        }

        /**
         * Gets the value of the betype property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getBETYPE() {
            return betype;
        }

        /**
         * Sets the value of the betype property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setBETYPE(String value) {
            this.betype = value;
        }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *       &lt;attribute name="TRANSFORMTYPE" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="decompression"/>
     *             &lt;enumeration value="decryption"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="TRANSFORMALGORITHM" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="TRANSFORMKEY" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="TRANSFORMBEHAVIOR" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
     *       &lt;attribute name="TRANSFORMORDER" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TransformFile {

        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "TRANSFORMTYPE", required = true)
        protected String transformtype;
        @XmlAttribute(name = "TRANSFORMALGORITHM", required = true)
        protected String transformalgorithm;
        @XmlAttribute(name = "TRANSFORMKEY")
        protected String transformkey;
        @XmlAttribute(name = "TRANSFORMBEHAVIOR")
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object transformbehavior;
        @XmlAttribute(name = "TRANSFORMORDER", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger transformorder;

        /**
         * Gets the value of the id property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the transformtype property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getTRANSFORMTYPE() {
            return transformtype;
        }

        /**
         * Sets the value of the transformtype property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setTRANSFORMTYPE(String value) {
            this.transformtype = value;
        }

        /**
         * Gets the value of the transformalgorithm property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getTRANSFORMALGORITHM() {
            return transformalgorithm;
        }

        /**
         * Sets the value of the transformalgorithm property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setTRANSFORMALGORITHM(String value) {
            this.transformalgorithm = value;
        }

        /**
         * Gets the value of the transformkey property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getTRANSFORMKEY() {
            return transformkey;
        }

        /**
         * Sets the value of the transformkey property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setTRANSFORMKEY(String value) {
            this.transformkey = value;
        }

        /**
         * Gets the value of the transformbehavior property.
         * 
         * @return possible object is {@link Object }
         * 
         */
        public Object getTRANSFORMBEHAVIOR() {
            return transformbehavior;
        }

        /**
         * Sets the value of the transformbehavior property.
         * 
         * @param value
         *            allowed object is {@link Object }
         * 
         */
        public void setTRANSFORMBEHAVIOR(Object value) {
            this.transformbehavior = value;
        }

        /**
         * Gets the value of the transformorder property.
         * 
         * @return possible object is {@link BigInteger }
         * 
         */
        public BigInteger getTRANSFORMORDER() {
            return transformorder;
        }

        /**
         * Sets the value of the transformorder property.
         * 
         * @param value
         *            allowed object is {@link BigInteger }
         * 
         */
        public void setTRANSFORMORDER(BigInteger value) {
            this.transformorder = value;
        }

    }

}