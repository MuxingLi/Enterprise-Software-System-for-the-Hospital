package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(PatientResource.class.getCanonicalName());
	
	@Context
    private UriInfo uriInfo;
	
	private ProviderDtoFactory providerDtoFactory;
	
	
	@Inject
	IProviderServiceLocal providerService;
	
	public ProviderResource(){
		providerDtoFactory = new ProviderDtoFactory();
	}
	@GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }
	
	@POST
	@Path("addProvider")
    @Consumes("application/xml")
	public Response addProvider(ProviderRepresentation providerRep){
		try{
			ProviderDto dto = providerDtoFactory.createProviderDto();
			dto.setProviderId(providerRep.getProviderId());
			dto.setSpec(providerRep.getSpec());
			dto.setName(providerRep.getName());
			long id = providerService.addProvider(dto);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
		}catch(ProviderServiceExn e){
			throw  new WebApplicationException();
		}
	}
	
	@GET
	@Path("{id}")
    @Produces("application/xml")
	public ProviderRepresentation retriProvider(@PathParam("id") String id){
		try {
			long key = Long.parseLong(id);
			ProviderDto dto = providerService.retriProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(dto, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("byNPI")
    @Produces("application/xml")
	public ProviderRepresentation getProviderById(@QueryParam("npi") String providerId){
		try {
			long key = Long.parseLong(providerId);
			ProviderDto dto = providerService.getProviderByPId(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(dto, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(404);
		}
	}
		
	@POST
	@Path("{id}/treatment")
	@Consumes("application/xml")
    public Response addTreatment(TreatmentRepresentation t){
		try{
			TreatmentDto dto = t.getTreatment();
			long  tid = providerService.addTreatment(dto, dto.getProvider(), dto.getPatient());
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(tid));
			return Response.created(url).build();
			}catch(ProviderServiceExn e){
			throw new WebApplicationException(404);
		}catch(PatientNotFoundExn e){
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("{id}/treatments/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException(404);
    	} catch (TreatmentNotFoundExn e) {
    		throw new WebApplicationException(404);
    	}
    }
	
}