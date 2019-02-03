package edu.stevens.cs548.clinic.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClinicGateway implements IClinicGateway {
	
	private EntityManagerFactory emf;
	
	public ClinicGateway() {
		emf = Persistence.createEntityManagerFactory("ClinicDomain1");
	}
	
	@Override
	public IPatientFactory gePatientFactory() {
		return new PatientFactory();
	}

	@Override
	public IPatientDAO getPatientDAO() {
		EntityManager em = emf.createEntityManager();
		return new PatientDAO(em);
	}
	
	@Override
	public IProviderFactory getProviderFactory() {
		return new ProviderFactory();
	}

	@Override
	public IProviderDAO getProviderDAO() {

		EntityManager em = emf.createEntityManager();
		return new ProviderDAO(em);
	}

	@Override
	public ITreatmentFactory geTreatmentFactory() {
		return new TreatmentFactory();
	}

}
