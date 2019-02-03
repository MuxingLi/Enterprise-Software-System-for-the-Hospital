package edu.stevens.cs548.clinic.domain;

import java.util.Date;

public interface IPatientFactory {

	public static class PatientAgeExn extends Exception{
		private static final long serialVersionUID = 1L;
		public PatientAgeExn(String msg) {
			super(msg);
		}
	}
	
	public Patient createPatient(long pid, String name, Date dob, int age) throws PatientAgeExn;
}
