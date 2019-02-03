package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Patient
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="SearchPatientByNameDOB", 
            query="select p from Patient p where p.name = :name and p.birthDate = :birthDate"),
	@NamedQuery(name = "SearchPatientByPatientId",
			query = "select p from Patient p where p.patientId = :pid"),
	@NamedQuery(
			name = "SearchAllPatients", 
			query = "select p from Patient p")
})
@Table(name="PATIENT")
public class Patient implements Serializable {

	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	private long patientId;
	
	private int age;
	
	private String name;  
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@OneToMany(cascade = REMOVE, mappedBy = "patient")
	@OrderBy
	private List<Treatment> treatments;

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO(ITreatmentDAO treatmentDAO) {
		this.treatmentDAO = treatmentDAO;
	}
	
	@Transient
	private IProviderDAO providerDAO;
	public void setProviderDAO(IProviderDAO providerDAO) {
		this.providerDAO = providerDAO;
	}
	
	@Transient
	private ITreatmentFactory treatmentFactory;
	
	public void setTreatmentFactory(ITreatmentFactory treatmentFactory) {
		this.treatmentFactory = treatmentFactory;
	}

	public long addTreatment(Treatment t) {
		/*if (t.getProvider()==provider) {*/
		this.treatmentDAO.addTreatment(t);
		this.getTreatments().add(t);
/*		if(t.getPatient() != this)
			t.setPatient(this);*/
		return t.getId();
	}
	
	public long addDrugTreatment(String diagnosis, String drug, 
			float dosage,long provider_id) throws ProviderExn {
		
		/*DrugTreatment treatment = new DrugTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDosage(dosage);
		treatment.setDurg(drug);
		treatment.setProvider(provider);
		this.addTreatment(treatment);*/
		Provider provider;
		try {
			provider = providerDAO.getProviderByProviderId(provider_id);
			Treatment drugTreatment = treatmentFactory.createDrugTreatment(diagnosis, drug, dosage);
			drugTreatment.setProvider(provider);
			return this.addTreatment(drugTreatment);
		} catch (ProviderExn e) {
			throw new ProviderExn(e.toString());
		}
	}
		/*Treatment drugTreament = treatmentFactory.createDrugTreatment(diagnosis, drug, dosage);
		drugTreament.setProvider(provider);
		return this.addTreatment(drugTreament);
		}*/
	
	public long addSurgery(String diagnosis, Date date,
			long provider_id) throws ProviderExn {
		/*Surgery surgery = new Surgery();
		surgery.setDiagnosis(diagnosis);
		surgery.setDate(date);
		surgery.setProvider(provider);
		this.addTreatment(surgery);*/
		Provider provider;
		try {
			provider = providerDAO.getProviderByProviderId(provider_id);
			Treatment surgery = treatmentFactory.createSurgery(diagnosis, date);
			surgery.setProvider(provider);
			return this.addTreatment(surgery);
		} catch (ProviderExn e) {
			throw new ProviderExn(e.toString());
		}
		/*Treatment surgery = treatmentFactory.createSurgery(diagnosis, date);
		surgery.setProvider(provider);
		return this.addTreatment(surgery);*/
	}
	
	public long  addRadiology(String diagnosis, List<Date> dates,
			long provider_id) throws ProviderExn {
		/*Radiology radiology = new Radiology();
		radiology.setDiagnosis(diagnosis);
		radiology.setDates(dates);
		radiology.setProvider(provider);
		this.addTreatment(radiology);*/
		
		Provider provider;
		try {
			provider = providerDAO.getProviderByProviderId(provider_id);
			Treatment radiology = treatmentFactory.createRadiology(diagnosis, dates);
			radiology.setProvider(provider);
			return this.addTreatment(radiology);
		} catch (ProviderExn e) {
			throw new ProviderExn(e.toString());
		}
		
		
		/*Treatment radiology = treatmentFactory.createRadiology(diagnosis, dates);
		this.addTreatment(radiology);
		radiology.setProvider(provider);
		return radiology.getId();*/
	}
	
	//why this cannot delete its treatment?
	public void deleteTreatment(long tid) throws TreatmentExn{
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getPatient() != this) {
			throw new TreatmentExn("Inappropriate treatment access: "
					+ "patient = "+id+", treatment = "+tid);
		}
		treatmentDAO.deleteTreatment(t);
	}
	
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for(Treatment t: this.getTreatments()) {
			tids.add(t.getId());
		}
		return tids;
	}
	
	//why this cannot see itself treatment?
	public <T> T visitTreatment(long tid, ITreatmentVisitor<T> visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getPatient() != this) {
			throw new TreatmentExn("Inappropriate treatment access: "
					+ "patient = "+id+", treatment = "+tid);
		}
		return (T) (t.visist(visitor)+"provider :"+t.getProvider().getName());
	}
}
