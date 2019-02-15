package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.PatientType;

@XmlRootElement
public class PatientRepresentation extends PatientType {
	
	public List<LinkType> getLinksTreatments(){
		return this.getTreatments();
	}
	
	public PatientRepresentation() {
		super();
	}
	
	public static LinkType getPatientLink(long id, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("patient").path("{id}");
		String patientURI = ub.build(Long.toString(id)).toString();

		LinkType link = new LinkType();
		link.setUrl(patientURI);
		link.setRelation(Representation.RELATIONS_PATIENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	
	public PatientRepresentation(PatientDto patientDTO,UriInfo uriInfo) {
		this.id = patientDTO.getId();
		this.patientId = patientDTO.getPatientId();
		this.name = patientDTO.getName();
		this.dob = patientDTO.getDob();
		this.age = patientDTO.getAge();
		
		List<Long> treatments = patientDTO.getTreatments();
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		/*
		 * call getTreatments to initialize empty list.
		 */
		List<LinkType> links = this.getTreatments();
		for (long t : treatments) {
			LinkType link = new LinkType();
			UriBuilder ubTreatment = ub.clone().path("{tid}");
			String treatmentURI = ubTreatment.build(Long.toString(t)).toString();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATIONS_TREATMENT);
			link.setMediaType(Representation.MEDIA_TYPE);
			links.add(link);
		}
	}

}
