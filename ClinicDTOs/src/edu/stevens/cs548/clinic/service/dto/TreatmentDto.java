//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.12-b141219.1637 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.10.29 时间 07:17:32 下午 EDT 
//


package edu.stevens.cs548.clinic.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取treatmentId属性的值。
     * 
     */
    public long getTreatmentId() {
        return treatmentId;
    }

    /**
     * 设置treatmentId属性的值。
     * 
     */
    public void setTreatmentId(long value) {
        this.treatmentId = value;
    }

    /**
     * 获取diagnosis属性的值。
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
     * 设置diagnosis属性的值。
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
     * 获取drugTreatment属性的值。
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
     * 设置drugTreatment属性的值。
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
     * 获取radiology属性的值。
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
     * 设置radiology属性的值。
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
     * 获取surgery属性的值。
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
     * 设置surgery属性的值。
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
     * 获取patient属性的值。
     * 
     */
    public long getPatient() {
        return patient;
    }

    /**
     * 设置patient属性的值。
     * 
     */
    public void setPatient(long value) {
        this.patient = value;
    }

    /**
     * 获取provider属性的值。
     * 
     */
    public long getProvider() {
        return provider;
    }

    /**
     * 设置provider属性的值。
     * 
     */
    public void setProvider(long value) {
        this.provider = value;
    }

}
