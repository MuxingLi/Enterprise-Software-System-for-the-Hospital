package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());
	
	private static void info(String	m){
	    logger.info(m);
	}
	
	PatientDtoFactory padf;
	ProviderDtoFactory prdf;
	TreatmentDtoFactory tdf;

	/**
	 * Default constructor.
	 */
	public InitBean() {
		padf = new PatientDtoFactory();
		prdf = new ProviderDtoFactory();
		tdf = new TreatmentDtoFactory();
	}
    
	@Inject
	IPatientServiceLocal patientService;
	@Inject
	IProviderServiceLocal providerService;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		info("Your name here: Muxing Li");

		try {

			Calendar calendar = Calendar.getInstance();
			calendar.set(1984, 9, 4);
			info("test1:add patient");
			PatientDto pad1=padf.createPatientDto();
			pad1.setAge(33);
			pad1.setDob(calendar.getTime());
			pad1.setPatientId(11111111);
			pad1.setName("qwe");
			long paid1 = patientService.addPatient(pad1);
			info("patient with patnientId:"+paid1+" has been add to database");
			info("end test1");
			
			info("test2:get patient");
			PatientDto pad2 = patientService.getPatient(paid1);
			info("get patient with pid:"+pad2.getPatientId()+" and name:"+pad2.getName()+" success");
			PatientDto pad3 =  patientService.getPatientByPatId(11111111);
			info("get patient with id:"+pad3.getId()+" and name:"+pad3.getName()+" success");
			info("end test2");
			
			info("test3:add provider");
			ProviderDto prd1 = prdf.createProviderDto();
			prd1.setName("asdf");
			prd1.setSpec("zxcv");
			prd1.setProviderId(333333);
			long prid1 = providerService.addProvider(prd1);
			info("provider with providerId:"+prid1+" has been add to database");
			info("end test3");
			
			info("test4:get provider");
			ProviderDto prd2 = providerService.retriProvider(prid1);
			info("get provider with pid:"+prd2.getProviderId()+" and name:"+prd2.getName()+" success");
			ProviderDto prd3 = providerService.getProviderByPId(333333);
			info("get provider with id:"+prd3.getId()+" and name:"+prd3.getName()+" success");
			info("end test4");
			
			info("test5:add Treatment");
			TreatmentDto td1 = tdf.createDrugTreatmentDto();
			td1.setDiagnosis("sdfbcvw");
			td1.getDrugTreatment().setDosage(1);
			td1.getDrugTreatment().setName("qsdfg3221");
			long tid1 = providerService.addTreatment(td1, prid1, paid1);
			info("Treatment record with treatmentid:"+tid1+" has been add to database");
			info("end test5");
			
			info("test6:get treatment");
			TreatmentDto td2 = patientService.getTreatment(paid1, tid1);
			info("get treatment "+tid1+" from patient "+paid1+" success");
			TreatmentDto td3 = providerService.getTreatment(prid1, tid1);
			info("get treatment "+tid1+" from provider "+prid1+" success");
			info("end test6");
		} catch (PatientServiceExn e) {
			info(e.getMessage());
		} catch (ProviderServiceExn e){
			info(e.getMessage());
		}
			
	}

}
