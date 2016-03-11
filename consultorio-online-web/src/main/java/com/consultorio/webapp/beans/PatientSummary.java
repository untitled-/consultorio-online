package com.consultorio.webapp.beans;

import java.util.Date;

public class PatientSummary {
	private Long id;
	private String firstName;
	private String lastName;
	private Date lastConsultation;
	private Date nextConsultation;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the lastConsultation
	 */
	public Date getLastConsultation() {
		return lastConsultation;
	}
	/**
	 * @param lastConsultation the lastConsultation to set
	 */
	public void setLastConsultation(Date lastConsultation) {
		this.lastConsultation = lastConsultation;
	}
	/**
	 * @return the nextConsultation
	 */
	public Date getNextConsultation() {
		return nextConsultation;
	}
	/**
	 * @param nextConsultation the nextConsultation to set
	 */
	public void setNextConsultation(Date nextConsultation) {
		this.nextConsultation = nextConsultation;
	}
	
	
	
}
