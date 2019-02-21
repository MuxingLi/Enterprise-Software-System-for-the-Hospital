package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;

public interface IPatientService {

	public class PatientServiceExn extends Exception {

		private static final long serialVersionUID = 1L;

		public PatientServiceExn(String m) {
			super(m);
		}
	}

	public class PatientNotFoundExn extends PatientServiceExn {

		private static final long serialVersionUID = 1L;

		public PatientNotFoundExn(String m) {
			super(m);
		}
	}

	public class TreatmentNotFoundExn extends PatientServiceExn {

		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExn(String m) {
			super(m);
		}
	}

	public long createPatient(String name, Date dob, long patientId, int age) throws PatientServiceExn;

	public PatientDto getpatientById(long id) throws PatientServiceExn;

	public PatientDto getPatientBypatId(long id) throws PatientServiceExn;

	public PatientDto[] getPatientByNameDob(String name, Date dob);

	public void deletePatient(String name, long id) throws PatientServiceExn;

	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public long addSurgery(long id, String diagnosis, Date date, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public long addRadiology(long id, String diagnosis, List<Date> dates, long provider_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn,PatientServiceExn; 

	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	
	public String siteInfo();

}
