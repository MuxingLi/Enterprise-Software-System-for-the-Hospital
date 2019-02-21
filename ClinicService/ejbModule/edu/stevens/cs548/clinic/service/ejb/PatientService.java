package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IPatientFactory.PatientAgeExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentVisitor;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;

/**
 * Session Bean implementation class PatientService
 * 
 * @param <T>
 */
@Stateless(name = "PatientServiceBean")
public class PatientService<T> implements IPatientServiceLocal, IPatientServiceRemote {

	private PatientFactory patientFactory;

	private IPatientDAO patientDAO;

	/**
	 * Default constructor.
	 */
	public PatientService() {
		patientFactory = new PatientFactory();
	}

	@PersistenceContext(unitName = "ClinicDomain1")
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		patientDAO = new PatientDAO(em);
	}

	/**
	 * @throws PatientServiceExn
	 * @see IPatientService#getpatient(long)
	 */
	public PatientDto getpatientById(long id) throws PatientServiceExn {

		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return new PatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	/**
	 * @see IPatientService#getPatientByNameDob(String, Date)
	 */
	public PatientDto[] getPatientByNameDob(String name, Date dob) {
		List<Patient> patients = patientDAO.getPatientByNameDob(name, dob);
		PatientDto[] dtos = new PatientDto[patients.size()];
		for (int i = 0; i < dtos.length; i++) {
			dtos[i] = new PatientDto(patients.get(i));
		}
		return dtos;
	}

	/**
	 * @throws PatientServiceExn
	 * @see IPatientService#deletePatient(String, long)
	 */
	public void deletePatient(String name, long id) throws PatientServiceExn {
		Patient pat;
		try {
			pat = patientDAO.getPatientByDbId(id);
			if (!name.equals(pat.getName())) {
				throw new PatientServiceExn("try to delete wrong patient: name = " + name);
			} else {
				patientDAO.delPatient(pat);
			}
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}

	}

	/**
	 * @throws PatientServiceExn
	 * @see IPatientService#getPatientBypatId(long)
	 */
	public PatientDto getPatientBypatId(long id) throws PatientServiceExn {

		try {
			Patient patient = patientDAO.getPatientByPatientId(id);
			return new PatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	/**
	 * @throws PatientServiceExn
	 * @see IPatientService#createPatient(String, Date, long, int)
	 */
	public long createPatient(String name, Date dob, long patientId, int age) throws PatientServiceExn {

		try {
			Patient patient = patientFactory.createPatient(patientId, name, dob, age);
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientAgeExn e) {
			throw new PatientServiceExn(e.toString());
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}

	}

	@Override
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addDrugTreatment(diagnosis, drug, dosage, provider_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	@Override
	public long addSurgery(long id, String diagnosis, Date date, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addSurgery(diagnosis, date, provider_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	@Override
	public long addRadiology(long id, String diagnosis, List<Date> dates, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			return patient.addRadiology(diagnosis, dates, provider_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	static class TreatmentPDO2DTO<T> implements ITreatmentVisitor<T> {

		private TreatmentDto dto;

		public TreatmentDto getDTO() {
			return dto;
		}

		@Override
		public T visitDrugTreatment(long id, String diagnosis, String drug, float dosage) {

			dto = new TreatmentDto();
			dto.setTreatmentId(id);
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = new DrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
			return (T) dto;
		}

		@Override
		public T visitRadiology(long id, String diagnosis, List<Date> dates) {

			dto = new TreatmentDto();
			dto.setTreatmentId(id);
			dto.setDiagnosis(diagnosis);
			RadiologyType radiology = new RadiologyType();
			radiology.setDate(dates);
			dto.setRadiology(radiology);
			return (T) dto;
		}

		@Override
		public T visitSurgery(long id, String diagnosis, Date date) {

			dto = new TreatmentDto();
			dto.setTreatmentId(id);
			dto.setDiagnosis(diagnosis);
			SurgeryType surgery = new SurgeryType();
			surgery.setDate(date);
			dto.setSurgery(surgery);
			return (T) dto;
		}

	}

	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {

		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDO2DTO<T> visitor = new TreatmentPDO2DTO<T>();
			for (int i = 0; i < tids.length; i++) {
				patient.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}
	

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			TreatmentDto treatmentDto = new TreatmentDto();
			TreatmentPDO2DTO<T> visitor = new TreatmentPDO2DTO<T>();
			patient.visitTreatment(tid, visitor);
			patient.visitTreatment(tid, visitor);
			treatmentDto = visitor.getDTO();
			return treatmentDto;
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {

		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			patient.deleteTreatment(tid);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;
	
	@Override
	public String siteInfo() {
		return siteInformation;
	}


}
