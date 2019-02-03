package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Radiology
 * Maven is in the configure option
 */
@Entity
@DiscriminatorValue("R")
@Embeddable
public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	@CollectionTable(name = "Radiology_dates")
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private List<Date> dates;

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> date) {
		this.dates = date;
	}

	public Radiology() {
		super();
		this.setTreatmentTYpe("R");
	}

	@Override
	public <T> T visist(ITreatmentVisitor<T> visitor) {
		//
		return visitor.visitRadiology(this.getId(), this.getDiagnosis(),this.getDates());
		
	}
   
}

