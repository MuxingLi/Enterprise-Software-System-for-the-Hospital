//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.12-b141219.1637 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2017.10.29 ʱ�� 07:17:32 ���� EDT 
//


package edu.stevens.cs548.clinic.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="treatmentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="diagnosis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="drug-treatment" type="{http://www.example.org/clinic/schemas/treatment}DrugTreatmentType"/>
 *           &lt;element name="radiology" type="{http://www.example.org/clinic/schemas/treatment}RadiologyType"/>
 *           &lt;element name="surgery" type="{http://www.example.org/clinic/schemas/treatment}SurgeryType"/>
 *         &lt;/choice>
 *         &lt;element name="patient" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "treatmentId",
    "diagnosis",
    "drugTreatment",
    "radiology",
    "surgery",
    "patient",
    "provider"
})
@XmlRootElement(name = "treatment-dto")
public class TreatmentDto {

    protected long treatmentId;
    @XmlElement(required = true)
    protected String diagnosis;
    @XmlElement(name = "drug-treatment")
    protected DrugTreatmentType drugTreatment;
    protected RadiologyType radiology;
    protected SurgeryType surgery;
    protected long patient;
    protected long provider;

    /**
     * ��ȡtreatmentId���Ե�ֵ��
     * 
     */
    public long getTreatmentId() {
        return treatmentId;
    }

    /**
     * ����treatmentId���Ե�ֵ��
     * 
     */
    public void setTreatmentId(long value) {
        this.treatmentId = value;
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
     * ��ȡpatient���Ե�ֵ��
     * 
     */
    public long getPatient() {
        return patient;
    }

    /**
     * ����patient���Ե�ֵ��
     * 
     */
    public void setPatient(long value) {
        this.patient = value;
    }

    /**
     * ��ȡprovider���Ե�ֵ��
     * 
     */
    public long getProvider() {
        return provider;
    }

    /**
     * ����provider���Ե�ֵ��
     * 
     */
    public void setProvider(long value) {
        this.provider = value;
    }

}
