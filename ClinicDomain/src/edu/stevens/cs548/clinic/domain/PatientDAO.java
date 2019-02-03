package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class PatientDAO implements IPatientDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDao;
	
	@Override
	public Patient getPatientByDbId(long id) throws PatientExn {
		Patient p = em.find(Patient.class, id);
		if(p == null)
			throw new PatientExn("Patient not found: primary key = "+id);
		else {
			p.setTreatmentDAO(new TreatmentDAO(this.em));
			return p;
			}
	}

	@Override
	public List<Patient> getPatientByNameDob(String name, Date dob) {
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByNameDOB", Patient.class)
									.setParameter("name", name)
									.setParameter("dob", dob);
		List<Patient> patients = query.getResultList();
		for(Patient p : patients) {
			p.setTreatmentDAO(new TreatmentDAO(this.em));
		}
		return patients;
	}
	
	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExn {
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientId", Patient.class)
									.setParameter("pid", pid);
		List<Patient> patients = query.getResultList();
		if(patients.size()>1)
			throw new PatientExn("Duplicate patient records: PatientId ="+pid);
		else if(patients.size()<1)
			throw new PatientExn("Patient not found: patient id = "+ pid);
		else {
			Patient p =  patients.get(0);
			p.setTreatmentDAO(new TreatmentDAO(this.em));
			return p;
		}
	}

	
	

	@Override
	public long addPatient(Patient patient) throws PatientExn {
		
		long pid = patient.getPatientId();

		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientId", Patient.class)
				.setParameter("pid", pid);
		List<Patient> patients = query.getResultList();
		if(patients.size()<1) {
			em.persist(patient);
			patient.setTreatmentDAO(new TreatmentDAO(this.em)); 
		}else {
			Patient patient2 = patients.get(0);
			throw new PatientExn("Insertion : Patient with patient id ("+pid+") "
					+ "	already exists.\n** Name :"+
								patient2.getName());
		}
		return pid;
	}

	@Override
	public void delPatient(Patient patient) throws PatientExn {
		em.remove(patient);
	}
	
	public PatientDAO(EntityManager em) {
		this.em = em;
		this.treatmentDao = new TreatmentDAO(em);
	}

	@Override
	public void delPatients() {
		TypedQuery<Patient> query = em.createNamedQuery("SearchAllPatients",Patient.class);
		List<Patient> patients = query.getResultList();
		for(int i = 0; i < patients.size();i++) {
			em.remove(patients.get(i));
		}
	}

	@Override
	public List<Patient> getAllPatient() {
		TypedQuery<Patient> query = em.createNamedQuery("SearchAllPatients",Patient.class);
		List<Patient> patients = query.getResultList();
		for(Patient p : patients) {
			p.setTreatmentDAO(new TreatmentDAO(this.em));
		}
		return patients;
	}
	
	

}
