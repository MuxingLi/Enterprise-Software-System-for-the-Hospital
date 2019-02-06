package edu.stevens.cs548.clinic.service.dto.util;


import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Surgery;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto() {
		TreatmentDto td = factory.createTreatmentDto();
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		td.setDrugTreatment(dt);
		return td;
	}

	public TreatmentDto createRadiologyDto() {
		TreatmentDto td = factory.createTreatmentDto();
		RadiologyType dt = factory.createRadiologyType();
		td.setRadiology(dt);
		return td;
	}
	
	public TreatmentDto createSurgeryDto() {
		TreatmentDto td = factory.createTreatmentDto();
		SurgeryType dt = factory.createSurgeryType();
		td.setSurgery(dt);
		return td;
	}
	
	public TreatmentDto createDrugTreatmentDto(DrugTreatment t) {
		TreatmentDto td = createTreatmentDto(t);
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		dt.setDosage(t.getDosage());
		dt.setName(t.getDrug());
		td.setDrugTreatment(dt);
		return td;
	}
	
	public TreatmentDto createRadiologyDto(Radiology t) {
		TreatmentDto td = createTreatmentDto(t);
		RadiologyType r = factory.createRadiologyType();
		r.setDate(t.getDates());
		td.setRadiology(r);
		return td;
	}
	
	public TreatmentDto createSurgeryDto(Surgery t) {
		TreatmentDto td = createTreatmentDto(t);
		SurgeryType s = factory.createSurgeryType();
		s.setDate(t.getDate());
		td.setSurgery(s);
		return td;
	}
	
	public TreatmentDto createTreatmentDto(Treatment t) {
		TreatmentDto td = factory.createTreatmentDto();
		td.setTreatmentId(t.getId());
		td.setDiagnosis(t.getDiagnosis());
		return td;
	}
	
	
	
}
