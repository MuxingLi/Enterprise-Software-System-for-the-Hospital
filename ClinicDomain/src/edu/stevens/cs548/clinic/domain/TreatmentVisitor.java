package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;


public class TreatmentVisitor<T> implements ITreatmentVisitor<T> {

	@Override
	public T visitDrugTreatment(long id,  String diagnosis, String drug,
			float dosage) {
		return (T) (id+","+diagnosis+","+drug+","+dosage);
	}

	@Override
	public T visitSurgery(long id, String diagnosis,Date date) {
		return (T) (id+","+diagnosis+","+date);
	}

	@Override
	public T visitRadiology(long id, String diagnosis, List<Date> dates) {
		return (T) (id+","+diagnosis+","+dates);
	}


}
