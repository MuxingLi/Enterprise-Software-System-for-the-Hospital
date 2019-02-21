package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;

public interface IProviderService {

	public class ProviderServiceExn extends Exception {

		private static final long serialVersionUID = 1L;

		public ProviderServiceExn(String m) {
			super(m);
		}
	}

	public class ProviderNotFoundExn extends ProviderServiceExn {

		private static final long serialVersionUID = 1L;

		public ProviderNotFoundExn(String m) {
			super(m);
		}
	}

	public class TreatmentNotFoundExn extends ProviderServiceExn {

		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExn(String m) {
			super(m);
		}
	}
	
	public long createProvider(long NPI, String name, String specilization) throws ProviderServiceExn;

	public ProviderDto getProviderById(long id) throws ProviderServiceExn;

	public ProviderDto[] getProviderByNameSpe(String name, String spe);

	public void deleteProvider(String name, long id) throws ProviderServiceExn;

	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public long addSurgery(long id, String diagnosis, Date date, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public long addRadiology(long id, String diagnosis, List<Date> dates, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn;

	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;

	public void deleteTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	public long addTreatment(TreatmentDto dto,long prId, long pid) throws ProviderNotFoundExn, PatientNotFoundExn;
	
	public String siteInfo();
}
