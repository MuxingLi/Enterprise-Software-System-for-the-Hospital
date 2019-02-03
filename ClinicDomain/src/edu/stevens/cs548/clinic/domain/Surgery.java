package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.sun.glass.ui.TouchInputSupport;

/**
 * Entity implementation class for Entity: Surgery
 *
 */
@Entity
@DiscriminatorValue("S")
public class Surgery extends Treatment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Surgery() {
		super();
		this.setTreatmentTYpe("S");
	}

	@Override
	public <T> T visist(ITreatmentVisitor<T> visitor) {
		return visitor.visitSurgery(this.getId(), this.getDiagnosis(), this.getDate());
		
	}
   
}
