package edu.stevens.cs548.clinic.service.dto.util;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.RadDate;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;

public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
	private	ObjectFactory factory = new ObjectFactory();
	
	@Override
	public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug, float dosage){
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		dt.setDosage(dosage);
		dt.setName(drug);
		dto.setDrugTreatment(dt);			
		return	dto;					
	}
	
	@Override
	public TreatmentDto exportSurgery(long tid,
		 	   String diagnosis,
               Date date){
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		SurgeryType st = factory.createSurgeryType();
		st.setDate(date);
		dto.setSurgery(st);
		return dto;
	}					
	
	@Override
	public TreatmentDto exportRadiology(long tid,
			 String diagnosis,
			 List<RadDate> dates){
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		RadiologyType rt = factory.createRadiologyType();
		for(RadDate rd:dates) rt.getDate().add(rd.getDate());
		dto.setRadiology(rt);
		return dto;
	}
}
