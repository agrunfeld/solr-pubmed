//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.26 at 10:14:53 AM PDT 
//


package parsers.medline;

import javax.xml.bind.annotation.*;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "issn",
    "journalIssue",
    "title",
    "isoAbbreviation"
})
@XmlRootElement(name = "Journal")
public class Journal {

    @XmlElement(name = "ISSN")
    protected ISSN issn;
    @XmlElement(name = "JournalIssue", required = true)
    protected JournalIssue journalIssue;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "ISOAbbreviation")
    protected String isoAbbreviation;

    /**
     * Gets the value of the issn property.
     * 
     * @return
     *     possible object is
     *     {@link parsers.medline.ISSN }
     *
     */
    public ISSN getISSN() {
        return issn;
    }

    /**
     * Sets the value of the issn property.
     *
     * @param value
     *     allowed object is
     *     {@link parsers.medline.ISSN }
     *
     */
    public void setISSN(ISSN value) {
        this.issn = value;
    }

    /**
     * Gets the value of the journalIssue property.
     *
     * @return
     *     possible object is
     *     {@link parsers.medline.JournalIssue }
     *
     */
    public JournalIssue getJournalIssue() {
        return journalIssue;
    }

    /**
     * Sets the value of the journalIssue property.
     *
     * @param value
     *     allowed object is
     *     {@link parsers.medline.JournalIssue }
     *     
     */
    public void setJournalIssue(JournalIssue value) {
        this.journalIssue = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the isoAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISOAbbreviation() {
        return isoAbbreviation;
    }

    /**
     * Sets the value of the isoAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISOAbbreviation(String value) {
        this.isoAbbreviation = value;
    }

}
