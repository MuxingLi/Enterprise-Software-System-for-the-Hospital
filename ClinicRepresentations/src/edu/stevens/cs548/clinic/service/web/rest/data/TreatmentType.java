//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.12-b141219.1637 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2017.10.29 ʱ�� 02:59:29 ���� EDT 
//


package edu.stevens.cs548.clinic.service.web.rest.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TreatmentType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TreatmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/>
 *         &lt;element name="provider" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/>
 *         &lt;element name="diagnosis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="drug-treatment" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}DrugTreatmentType"/>
 *           &lt;element name="radiology" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}RadiologyType"/>
 *           &lt;element name="surgery" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}SurgeryType"/>
 *         &lt;/choice>
 *         &lt;element name="id" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentType", propOrder = {
    "patient",
    "provider",
    "diagnosis",
    "drugTreatment",
    "radiology",
    "surgery",
    "id"
})
public class TreatmentType {

    @XmlElement(required = true)
    protected LinkType patient;
    @XmlElement(required = true)
    protected LinkType provider;
    @XmlElement(required = true)
    protected String diagnosis;
    @XmlElement(name = "drug-treatment")
    protected DrugTreatmentType drugTreatment;
    protected RadiologyType radiology;
    protected SurgeryType surgery;
    @XmlElement(required = true)
    protected LinkType id;

    /**
     * ��ȡpatient���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getPatient() {
        return patient;
    }

    /**
     * ����patient���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setPatient(LinkType value) {
        this.patient = value;
    }

    /**
     * ��ȡprovider���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getProvider() {
        return provider;
    }

    /**
     * ����provider���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setProvider(LinkType value) {
        this.provider = value;
    }

    /**
     * ��ȡdiagnosis���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * ����diagnosis���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosis(String value) {
        this.diagnosis = value;
    }

    /**
     * ��ȡdrugTreatment���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DrugTreatmentType }
     *     
     */
    public DrugTreatmentType getDrugTreatment() {
        return drugTreatment;
    }

    /**
     * ����drugTreatment���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DrugTreatmentType }
     *     
     */
    public void setDrugTreatment(DrugTreatmentType value) {
        this.drugTreatment = value;
    }

    /**
     * ��ȡradiology���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadiologyType }
     *     
     */
    public RadiologyType getRadiology() {
        return radiology;
    }

    /**
     * ����radiology���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadiologyType }
     *     
     */
    public void setRadiology(RadiologyType value) {
        this.radiology = value;
    }

    /**
     * ��ȡsurgery���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SurgeryType }
     *     
     */
    public SurgeryType getSurgery() {
        return surgery;
    }

    /**
     * ����surgery���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SurgeryType }
     *     
     */
    public void setSurgery(SurgeryType value) {
        this.surgery = value;
    }

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setId(LinkType value) {
        this.id = value;
    }

}
