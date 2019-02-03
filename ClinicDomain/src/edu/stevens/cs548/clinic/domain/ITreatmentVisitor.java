package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentVisitor<T> {
	
	public T visitDrugTreatment(long id, String diagnosis, String drug, float dosage);
	
	public T visitRadiology(long id,String diagnosis, List<Date> dates);
	
	public T visitSurgery(long id, String diagnosis, Date date);

}
