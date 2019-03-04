package edu.stevens.cs548.clinic.research.jms;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.billing.service.IResearchService.DrugTreatmentDTO;
import edu.stevens.cs548.clinic.billing.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.research.service.DrugTreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: DrugTreatmentListener
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/Treatment"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
						propertyName = "messageSelector", propertyValue = "treatmentType='Drug'"
				)}, 
		mappedName = "jms/Treatment")
public class DrugTreatmentListener implements MessageListener {

	@Inject
	@EJB(beanName="ResearchServiceBean")
	IResearchServiceLocal researchservice;
    /**
     * Default constructor. 
     */
    public DrugTreatmentListener() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext(unitName="ClinicDomain")
    private EntityManager em;
    
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	ObjectMessage objMessage = (ObjectMessage) message;
    	try {
			TreatmentDto treatment = (TreatmentDto) objMessage.getObject();
			if(treatment.getDrugTreatment()==null) {
				//Log the error
			} else if(treatment.getDrugTreatment()!=null) {
				DrugTreatmentDTO dto = new DrugTreatmentDtoFactory().createDrugTreatmentDTO();
				dto.setDosage(treatment.getDrugTreatment().getDosage());
				dto.setDrugName(treatment.getDrugTreatment().getName());
				dto.setPatientId(treatment.getPatient());
				dto.setTreatmentId(treatment.getProvider());
				researchservice.addDrugTreatmentRecord(dto);
			}else if (treatment.getSurgery()!=null) {
				//surgery
//				SurgeryDTO dto = new SurgeryDTO().createSurgeryDTO();
//				dto.setRadDate(treatment.getSurgery().getDate());
//				dto.setTreatmentId(treatment.getId());
//				dto.setDiagnosis(treatment.getDiagnosis());
			}else if (treatment.getRadiology()!=null) {
				//radiology
//				RadiologyDTO dto = new RadiologyDTO().createRadiologyDTO();
//				dto.setRadDate(treatment.getRadiology().getDate());
//				dto.setTreatmentId(treatment.getId());
//				dto.setDiagnosis(treatment.getDiagnosis());
			}
    	} catch (JMSException e) {
			// Log the error
		}        
    }

}