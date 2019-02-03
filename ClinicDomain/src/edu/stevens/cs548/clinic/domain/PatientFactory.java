package edu.stevens.cs548.clinic.domain;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@Override
	public Patient createPatient(long pid, String name, Date dob, int age) throws PatientAgeExn {
		Patient p = new Patient();
		p.setPatientId(pid);
		p.setName(name);
		p.setBirthDate(dob);
		p.setAge(age);
		Calendar cal=Calendar.getInstance();  
	    cal.setTime(dob);
		if (age != (Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR) )) {
			throw new PatientAgeExn(
							"age is incorrect given the supplied date of birth age: " + age + " dob: " + dob+
							" age should be : "+(Calendar.getInstance().get(Calendar.YEAR) - dob.getYear())
					);
		}
		return p;
	}
}
