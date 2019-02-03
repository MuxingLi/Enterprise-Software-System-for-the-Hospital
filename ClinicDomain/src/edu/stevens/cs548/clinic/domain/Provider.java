package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

import static javax.persistence.CascadeType.REMOVE;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="SearchProviderByNameSpe", 
            query="select p from Provider p "
            		+ "where p.name = :name and p.specilization = :specilization"),
	@NamedQuery(name = "SearchProviderByProviderId",
			query = "select p from Provider p where p.NPI = :npi"),
	@NamedQuery(
			name = "SearchAllProviders", 
			query = "select p from Provider p")
})
@Table(name="PROVIDER")
public class Provider implements Serializable {

	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private long NPI;
	
	private String name;
	
	private String specilization;
	
	public long getNPI() {
		return NPI;
	}

	public void setNPI(long nPI) {
		NPI = nPI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecilization() {
		return specilization;
	}

	public void setSpecilization(String specilization) {
		this.specilization = specilization;
	}

	@OneToMany(mappedBy = "provider")
	@OrderBy
	private List<Treatment> treatments;

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO(ITreatmentDAO treatmentDAO) {
		this.treatmentDAO = treatmentDAO;
	}
	
	@Transient
	private IPatientDAO patientDAO;
	public void setPatientDAO(IPatientDAO patientDAO) {
		this.patientDAO = patientDAO;
	}
	
	@Transient
	private ITreatmentFactory treatmentFactory;
	
	public void setTreatmentFactory(ITreatmentFactory treatmentFactory) {
		this.treatmentFactory = treatmentFactory;
	}
	
	public long addTreatment(Treatment t,Patient patient) {
			this.getTreatments().add(t);
			long id = patient.addTreatment(t);
			return id;
		
	}
	
	public long addDrugTreatment(String diagnosis,String drug, 
			float dosage,long patient_id) throws PatientExn{
		/*DrugTreatment drugTreatment = new DrugTreatment();
		drugTreatment.setDiagnosis(diagnosis);
		drugTreatment.setDosage(dosage);
		drugTreatment.setDurg(drug);
		drugTreatment.setPatient(patient);
		this.addTreatment(drugTreatment);*/
		
		try {
			Patient patient = patientDAO.getPatientByPatientId(patient_id);
			Treatment drugTreatment = treatmentFactory.createDrugTreatment(diagnosis, drug, dosage);
			return this.addTreatment(drugTreatment,patient);
		} catch (PatientExn e) {
			throw new PatientExn(e.toString());
		}
		/*Treatment drugTreatment = treatmentFactory.createDrugTreatment(diagnosis, drug, dosage);
		drugTreatment.setPatient(patient);
		return this.addTreatment(drugTreatment);*/
	}
	
	public long addSurgery(String diagnosis, Date date,long patient_id) throws PatientExn {
		/*Surgery surgery = new Surgery();
		surgery.setDiagnosis(diagnosis);
		surgery.setDate(date);
		surgery.setPatient(patient);
		this.addTreatment(surgery);*/
		
		try {
			Patient patient = patientDAO.getPatientByPatientId(patient_id);
			Treatment surgery = treatmentFactory.createSurgery(diagnosis, date);
			return this.addTreatment(surgery,patient);
		} catch (PatientExn e) {
			throw new PatientExn(e.toString());
		}
		
		
		/*Treatment surgery = treatmentFactory.createSurgery(diagnosis, date);
		surgery.setPatient(patient);
		return this.addTreatment(surgery);*/
	}
	
	public long addRadiology(String diagnosis, List<Date> dates,long patient_id) throws PatientExn{
		/*Radiology radiology = new Radiology();
		radiology.setDiagnosis(diagnosis);
		radiology.setDates(dates);
		radiology.setPatient(patient);
		this.addTreatment(radiology);*/
		try {
			Patient patient = patientDAO.getPatientByPatientId(patient_id);
			Treatment radiology = treatmentFactory.createRadiology(diagnosis, dates);
			return this.addTreatment(radiology,patient);
		} catch (PatientExn e) {
			throw new PatientExn(e.toString());
		}
		
		/*Treatment radiology = treatmentFactory.createRadiology(diagnosis, dates);
		radiology.setPatient(patient);
		return this.addTreatment(radiology);*/
	}
	
	//??????
	public void deleteTreatment(long tid) throws TreatmentExn{
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider ="+NPI+", treatment = "+tid);
		}
		treatmentDAO.deleteTreatment(t);
	}

	public <T> T visitTreatment(long tid, ITreatmentVisitor<T> visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider = "+NPI+", treatment = "+tid);
		}
		return (T) (t.visist(visitor)+" Patient : "+t.getPatient().getName());
	}
	
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for(Treatment t: this.getTreatments()) {
			tids.add(t.getId());
		}
		return tids;
	}
	
   
}
