package com.consultorio.webapp.ws.controller;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Disease;
import com.consultorio.core.dataaccess.entity.Gender;
import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;
import com.consultorio.core.dataaccess.entity.Patient;
import com.consultorio.core.services.PatientService;

@Component	
public class CxfRestServiceImpl implements CxfRestService {
	@Autowired
	PatientService patientService;

	public Response getEmployeeDetail(String employeeId) {

		patientService.createPatient(getTestPatient());

		if (employeeId == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok(patientService.getAllPatients()).build();
	}

	public Patient getTestPatient() {

		Address address = new Address();
		address.setStreet("4701 Staggerbrush Rd");
		address.setCity("Austin");
		address.setState("Texas");

		Disease disease = new Disease();
		disease.setCode("D123456789");
		disease.setName("Diabetes");
		disease.setType("DM1");

		Patient patient = new Patient();
		patient.setFirstName("Pedro" + System.currentTimeMillis());
		patient.setGender(Gender.MALE);
		patient.setAddress(address);

		HeredoFamilyBackground familyBackground = new HeredoFamilyBackground();
		familyBackground.addDisease(disease);

		patient.setHereditaryFamilyBackground(familyBackground);

		return patient;
	}

	@Override
	public Response savePatient(Patient patient) {
		Patient result = patientService.createPatient(getTestPatient());

		if (patient == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok(result).build();
	}
}