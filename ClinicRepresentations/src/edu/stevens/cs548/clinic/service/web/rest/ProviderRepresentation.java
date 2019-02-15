package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderType;

@XmlRootElement
public class ProviderRepresentation extends ProviderType {
	
	public List<LinkType> getLinksTreatments(){
		return this.getTreatments();
	}

	public ProviderRepresentation() {
		super();
	}

	public static LinkType getProviderLink(long id, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("provider").path("{id}");
		String providerURI = ub.build(Long.toString(id)).toString();
		LinkType link = new LinkType();
		link.setUrl(providerURI);
		link.setRelation(Representation.RELATIONS_PROVIDER);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	
	public ProviderRepresentation(ProviderDto providerDto,UriInfo uriInfo) {
		this.providerId = providerDto.getProviderId();
		this.name = providerDto.getName();
		this.specilization = providerDto.getSpecialization();
		List<Long> treatments = providerDto.getTreatments();
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
