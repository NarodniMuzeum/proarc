//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.21 at 01:10:09 AM CET 
//


package cz.cas.lib.proarc.nsesss2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Prvek pro zaznamenání časových údajů počátku a konce řádné platnosti certifikátu vydaného poskytovatelem certifikačních služeb.
 * 
 * <p>Java class for tPlatnost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tPlatnost">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PlatnostOd" type="{http://www.mvcr.cz/nsesss/v2}tDatum"/>
 *         &lt;element name="PlatnostDo" type="{http://www.mvcr.cz/nsesss/v2}tDatum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPlatnost", namespace = "http://www.mvcr.cz/nsesss/v2", propOrder = {
    "platnostOd",
    "platnostDo"
})
public class TPlatnost {

    @XmlElement(name = "PlatnostOd", namespace = "http://www.mvcr.cz/nsesss/v2", required = true)
    protected TDatum platnostOd;
    @XmlElement(name = "PlatnostDo", namespace = "http://www.mvcr.cz/nsesss/v2", required = true)
    protected TDatum platnostDo;

    /**
     * Gets the value of the platnostOd property.
     * 
     * @return
     *     possible object is
     *     {@link TDatum }
     *     
     */
    public TDatum getPlatnostOd() {
        return platnostOd;
    }

    /**
     * Sets the value of the platnostOd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDatum }
     *     
     */
    public void setPlatnostOd(TDatum value) {
        this.platnostOd = value;
    }

    /**
     * Gets the value of the platnostDo property.
     * 
     * @return
     *     possible object is
     *     {@link TDatum }
     *     
     */
    public TDatum getPlatnostDo() {
        return platnostDo;
    }

    /**
     * Sets the value of the platnostDo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDatum }
     *     
     */
    public void setPlatnostDo(TDatum value) {
        this.platnostDo = value;
    }

}
