package edu.stevens.cs548.clinic.service.web.rest;

import java.util.Date;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;


import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.ObjectFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;

@XmlRootElement
public class TreatmentRepresentation extends TreatmentType {
	
		private ObjectFactory repFactory = new ObjectFactory();
		
		public LinkType getLinkPatient() {
			return this.getPatient();
		}
		
		public LinkType getLinkProvider() {
			return this.getProvider();
		}
		
		private TreatmentDtoFactory treatmentDtoFactory = new TreatmentDtoFactory();
		public TreatmentRepresentation() {
			super();
			treatmentDtoFactory = new TreatmentDtoFactory();
		}
		
		public static LinkType getTreatmentLink(long tid, UriInfo uriInfo) {
			UriBuilder ub = uriInfo.getBaseUriBuilder();
			ub.path("treatment");
			UriBuilder ubTreatment = ub.clone().path("{tid}");
			String treatmentURI = ubTreatment.build(Long.toString(tid)).toString();
		
			LinkType link = new LinkType();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATIONS_TREATMENT);
			link.setMediaType(Representation.MEDIA_TYPE);
			return link;
		}
		
		
		
		public TreatmentRepresentation (TreatmentDto dto, UriInfo uriInfo) {
			this();
			this.id = getTreatmentLink(dto.getTreatmentId(), uriInfo);
			this.patient =  PatientRepresentation.getPatientLink(dto.getPatient(), uriInfo);
			this.provider = ProviderRepresentation.getProviderLink(dto.getProvider(), uriInfo);
			this.diagnosis = dto.getDiagnosis();
			if (dto.getDrugTreatment() != null) {
				this.drugTreatment = repFactory.createDrugTreatmentType();
				this.drugTreatment.setDosage(dto.getDrugTreatment().getDosage());
				this.drugTreatment.setName(dto.getDrugTreatment().getName());
			} else if (dto.getSurgery() != null) {
				this.surgery = repFactory.createSurgeryType();
				this.surgery.setDate(dto.getSurgery().getDate());
			} else if (dto.getRadiology() != null) {
				this.radiology = repFactory.createRadiologyType();
				for (Date d : dto.getRadiology().getDate()) {
					this.radiology.getDate().add(d);
				}
			}
		}
		
		public TreatmentDto getTreatment() {
			TreatmentDto t = null;
			if (this.getDrugTreatment() != null) {
				t = treatmentDtoFactory.createDrugTreatmentDto();
				//t.setId(Representation.getId(id));
				t.setPatient(Representation.getId(patient));
				t.setProvider(Representation.getId(provider));
				t.setDiagnosis(diagnosis);
				t.getDrugTreatment().setDosage(drugTreatment.getDosage());
				t.getDrugTreatment().setName(drugTreatment.getName());
			} else if (this.getSurgery() != null) {
				t=treatmentDtoFactory.createSurgeryDto();
				//t.setId(Representation.getId(id));
				t.setPatient(Representation.getId(patient));
				t.setProvider(Representation.getId(provider));
				t.setDiagnosis(diagnosis);
				t.getSurgery().setDate(this.surgery.getDate());
			} else if (this.getRadiology() != null) {
				t=treatmentDtoFactory.createRadiologyDto();
				//t.setId(Representation.getId(id));
				t.setPatient(Representation.getId(patient));
				t.setProvider(Representation.getId(provider));
				t.setDiagnosis(diagnosis);
				for(Date d:this.radiology.getDate()) t.getRadiology().getDate().add(d);
			}
			return t;
		}

}
