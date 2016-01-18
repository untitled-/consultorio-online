package com.consultorio.webapp.ws.controller.impl;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Disease;
import com.consultorio.core.dataaccess.entity.Gender;
import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;
import com.consultorio.core.dataaccess.entity.Patient;
import com.consultorio.core.services.PatientService;
import com.consultorio.webapp.ws.controller.api.PatientWebService;

@Component
public class PatientWebServiceImpl implements PatientWebService {
	private static Logger LOG = LoggerFactory.getLogger(PatientWebServiceImpl.class);

	@Autowired
	PatientService patientService;

	@PostConstruct
	public void initIt() throws Exception {
		patientService.savePatient(getTestPatient());
	}

	@Override
	public Response getPatient(String patientId) {
		try {
			if (patientId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			Patient patient = patientService.getPatientById(Long.parseLong(patientId));
			return Response.ok(patient).build();

		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response updatePatient(String patientId, Patient patient) {

		if (patient == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (patientId != null) {
			patient.setId(Long.parseLong(patientId));
			Patient old = patientService.getPatientById(Long.parseLong(patientId));
			if(old == null){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			BeanUtils.copyProperties(patient, old);
			patient = old;
		}
		LOG.info(patient.getId().toString());
		Patient result = patientService.savePatient(patient);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

	@Override
	public Response getPatients() {
		try {
			// patientService.savePatient(getTestPatient());
			return Response.ok(patientService.getAllPatients()).build();
		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	private Patient getTestPatient() {

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
	public Response deletePatient(String patientId) {
		try {
			if (patientId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			patientService.deletePatientById(Long.parseLong(patientId));
			return Response.ok().build();

		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response savePatient(Patient patient) {
		if (patient == null) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (patient.getId() != null) {
			Patient old = patientService.getPatientById(patient.getId());
			BeanUtils.copyProperties(patient, old);
			patient = old;
		}

		Patient result = patientService.savePatient(patient);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

}