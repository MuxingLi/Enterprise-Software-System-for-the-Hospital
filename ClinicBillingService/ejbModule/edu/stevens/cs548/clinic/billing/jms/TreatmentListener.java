package edu.stevens.cs548.clinic.billing.jms;

import java.util.Random;

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


import edu.stevens.cs548.clinic.billing.service.BillingDtoFactory;
import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/Treatment"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {

    /**
     * Default constructor. 
     */
	@Inject
	@EJB(beanName="BillingServiceBean")
	IBillingServiceLocal billingService;
    public TreatmentListener() {
        // TODO Auto-generated constructor stub
    }
	
    @PersistenceContext(unitName="ClinicDomain")
    private EntityManager em;
    
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage)message;
        try {
            TreatmentDto treatment = (TreatmentDto) objMessage.getObject();
            BillingDTO dto = new BillingDtoFactory().createBillingDTO();
            Random generator = new Random();
            float amount = generator.nextFloat() * 500;
            dto.setAmount(amount);
            dto.setTreatmentId(treatment.getId());
            billingService.addBillingRecord(dto);
        }catch (JMSException e) {
        	// log the exception 
		}
        
        
    }


}
