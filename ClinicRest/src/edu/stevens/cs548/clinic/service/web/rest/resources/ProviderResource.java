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

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.ProviderService;
import edu.stevens.cs548.clinic.service.web.rest.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	private static Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    
    private TreatmentDtoFactory treatmentDtoFactory;
    public ProviderResource() {
        // TODO Auto-generated constructor stub
		treatmentDtoFactory = new TreatmentDtoFactory();
    }
    
    @Inject
    private IProviderServiceLocal providerService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

    
    /**
     * Retrieves representation of an instance of ProviderResource
     * @return an instance of PatientRepresentation
     */
    @GET
    @Path("{providerId}")
    @Produces("application/xml")
    public ProviderRepresentation getProviderById(@PathParam("providerId") String npi) {
    	try {
			ProviderDto providerDto = providerService.getProviderById(Long.parseLong(npi));
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDto, context);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException();
		}
    }
    
	@POST
	@Consumes("application/xml")
	public Response addProvider(ProviderRepresentation providerRep) {
		try {
			long id = providerService.createProvider(providerRep.getProviderId(), 
					providerRep.getName(), providerRep.getSpecilization());
			UriBuilder ub = context.getAbsolutePathBuilder().path("{npi}");
			URI url = ub.build(Long.toString(id));
			return Response.created(url).build();
		} catch (IProviderService.ProviderServiceExn e) {
			throw new WebApplicationException();
		}
	}
	
	@POST
	@Path("{npi}")
	@Consumes("application/xml")
    public Response addTreatment(@PathParam("npi") String npi,TreatmentRepresentation t){
		try{
			TreatmentDto dto = t.getTreatment();
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info("---------------------------------------------------------------------------------------------------");
			logger.info(dto.getDiagnosis()+"  "+dto.getPatient()+" "+dto.getDrugTreatment().getName());
			long  tid = providerService.addTreatment(dto, Long.parseLong(npi), dto.getPatient());
			UriBuilder ub = context.getAbsolutePathBuilder().path("{npi}");
			URI url = ub.build(npi);
			url = ub.build(Long.toString(tid));
			return Response.created(url).build();
			}catch(ProviderServiceExn e){
			throw new WebApplicationException(404);
		}catch(PatientNotFoundExn e){
			throw new WebApplicationException(404);  
		}
	}
	
    @GET
    @Path("{npi}/treatment")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("npi") String npi,
    		@QueryParam("tid") String id) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(npi), Long.parseLong(id));
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, context);
    		return treatmentRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException();
		}
    }
    

    
    
    

}