package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.PatientService.TreatmentPDO2DTO;

/**
 * Session Bean implementation class ProviderService
 * 
 * @param <T>
 */
@Stateless(name = "ProviderServiceBean")
public class ProviderService<T> implements IProviderServiceLocal, IProviderServiceRemote {

	private static Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());
	private IProviderFactory providerFactory;

	private ITreatmentFactory treatmentFactory;
	private IProviderDAO providerDAO;
	private IPatientDAO patientDAO;

	/**
	 * Default constructor.
	 */
	public ProviderService() {
		providerFactory = new ProviderFactory();
		treatmentFactory = new TreatmentFactory();
	}

	@PersistenceContext(unitName = "ClinicDomain1")
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		providerDAO = new ProviderDAO(em);
		patientDAO = new PatientDAO(em);
	}

	/**
	 * @see IProviderService#getTreatments(long, long[])
	 */
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDO2DTO<T> visitor = new TreatmentPDO2DTO<T>();
			for (int i = 0; i < tids.length; i++) {
				provider.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			TreatmentDto treatment = new TreatmentDto();
			TreatmentPDO2DTO<T> visitor = new TreatmentPDO2DTO<T>();
			provider.visitTreatment(tid, visitor);
			treatment = visitor.getDTO();
			return treatment;
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#getProviderByNameSpe(String, String)
	 */
	public ProviderDto[] getProviderByNameSpe(String name, String spe) {
		List<Provider> providers = providerDAO.getProviderByNameSpe(name, spe);
		ProviderDto[] dtos = new ProviderDto[providers.size()];
		for (int i = 0; i < dtos.length; i++) {
			dtos[i] = new ProviderDto(providers.get(i));
		}
		return dtos;
	}

	/**
	 * @see IProviderService#getProviderById(long)
	 */
	public ProviderDto getProviderById(long id) throws ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			return new ProviderDto(provider);
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#deleteProvider(String, long)
	 */
	public void deleteProvider(String name, long id) throws ProviderServiceExn {

		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			if (!name.equals(provider.getName())) {
				throw new ProviderServiceExn("try to delete wrong provider: name = " + name);
			} else {
				providerDAO.delProvider(provider);
			}
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}

	}

	/**
	 * @see IProviderService#addSurgery(long, String, Date, long)
	 */
	public long addSurgery(long id, String diagnosis, Date date, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			return provider.addSurgery(diagnosis, date, patient_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#deleteTreatment(long, long)
	 */
	public void deleteTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			provider.deleteTreatment(tid);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#addRadiology(long, String, List<Date>, long)
	 */
	public long addRadiology(long id, String diagnosis, List<Date> dates, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			return provider.addRadiology(diagnosis, dates, patient_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#createProvider(long, String, String)
	 */
	public long createProvider(long NPI, String name, String specilization) throws ProviderServiceExn {
		try {
			Provider provider = providerFactory.createProvider(NPI, name, specilization);
			providerDAO.addProvider(provider);
			return provider.getNPI();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#addDrugTreatment(long, String, String, float, long)
	 */
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage, long patient_id)
			throws PatientNotFoundExn, ProviderNotFoundExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(id);
			return provider.addDrugTreatment(diagnosis, drug, dosage, patient_id);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;

	@Override
	public String siteInfo() {
		return siteInformation;
	}

	@Override
	public long addTreatment(TreatmentDto dto,long prId, long pid) throws ProviderNotFoundExn, PatientNotFoundExn{
		try {
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			
			logger.info("prid ="+prId+"  pid = "+pid);
			Provider provider = providerDAO.getProviderByProviderId(prId);
			Patient patient = patientDAO.getPatientByDbId(pid);
			Treatment t = null;
			if(dto.getDrugTreatment()!=null){
				t = treatmentFactory.createDrugTreatment(dto.getDiagnosis(), dto.getDrugTreatment().getName(), dto.getDrugTreatment().getDosage());
			}else if(dto.getRadiology()!=null){
				t = treatmentFactory.createRadiology(dto.getDiagnosis(), dto.getRadiology().getDate());
			}else if(dto.getSurgery()!=null){
				t = treatmentFactory.createSurgery(dto.getDiagnosis(), dto.getSurgery().getDate());
			}
			
			if(t==null){
				throw new ProviderExn("treatment is null!");
			}
			t.setPatient(patient);
			t.setProvider(provider);
			logger.info("prid ="+prId+"  pid = "+pid);
			return provider.addTreatment(t,patient);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}catch (PatientExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

} 
