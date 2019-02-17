package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.Date;

import javax.ejb.EJB;
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
import javax.xml.bind.DatatypeConverter;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;
import edu.stevens.cs548.clinic.service.web.rest.PatientRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;

@Path("/patient")
@RequestScoped
public class PatientResource {
	@SuppressWarnings("unused")
	@Context
	private UriInfo context;

	/**
	 * Default constructor.
	 */
	public PatientResource() {
		// TODO Auto-generated constructor stub
	}

	//@EJB(beanName = "PatientServiceBean")
	@Inject
	private IPatientServiceLocal patientService;
	
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return patientService.siteInfo();
    }

	/**
	 * Query methods for patient resources
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public PatientRepresentation getPatient(@PathParam("id") String id) {
		try {
			PatientDto patientDTO = patientService.getpatientById(Long.parseLong(id));
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, context);
			return patientRep;
		} catch (PatientServiceExn e) {
			throw new WebApplicationException(404);
		}
	}

	@GET
	@Path("patientId")
	@Produces("application/xml")
	public PatientRepresentation getPatientByPatientId(@QueryParam("patientId") String patientId) {
		try {
			PatientDto patientDTO = patientService.getPatientBypatId(Long.parseLong(patientId));
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, context);
			return patientRep;
		} catch (PatientServiceExn e) {
			throw new WebApplicationException(404);
		}
	}

	@GET
	@Path("namedob")
	@Produces("application/xml")
	public PatientRepresentation[] getPatientByNameDob(@QueryParam("name") String name, @QueryParam("dob") String dob) {
		Date birthDate = DatatypeConverter.parseDate(dob).getTime();
		PatientDto[] patientDTO = patientService.getPatientByNameDob(name, birthDate);
		PatientRepresentation[] patientReps = new PatientRepresentation[patientDTO.length];
		for (int i = 0; i < patientDTO.length; i++) {
			patientReps[i] = new PatientRepresentation(patientDTO[i], context);
		}
		return patientReps;
	}

	@POST
	@Consumes("application/xml")
	public Response addPatient(PatientRepresentation patientRep) {
		try {
			long id = patientService.createPatient(patientRep.getName(), patientRep.getDob(), patientRep.getPatientId(),
					patientRep.getAge());
			UriBuilder ub = context.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(id));
			return Response.created(url).build();
		} catch (IPatientService.PatientServiceExn e) {
			throw new WebApplicationException();
		}
	}
	
	@GET
	@Path("{id}/treatment")
	@Produces("application/xml")
    public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @QueryParam("treatmentId") String tid) {
    	try {
    		TreatmentDto treatment = patientService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, context);
    		return treatmentRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException(404);
    	}
    }
	
	

}