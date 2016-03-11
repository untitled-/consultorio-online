package com.consultorio.webapp.ws.controller.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Consultation;
import com.consultorio.core.dataaccess.entity.Disease;
import com.consultorio.core.dataaccess.entity.Gender;
import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;
import com.consultorio.core.dataaccess.entity.Patient;
import com.consultorio.core.services.PatientService;
import com.consultorio.webapp.beans.PatientSummary;
import com.consultorio.webapp.ws.controller.api.PatientWebService;

@Component
public class PatientWebServiceImpl implements PatientWebService {
	private static Logger LOG = LoggerFactory.getLogger(PatientWebServiceImpl.class);

	@Autowired
	PatientService patientService;

	@PostConstruct
	public void initIt() throws Exception {
		patientService.save(getTestPatient());
	}

	@Override
	public Response getPatient(String patientId) {
		try {
			if (patientId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			Patient patient = patientService.getById(Long.parseLong(patientId));
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
			Patient old = patientService.getById(Long.parseLong(patientId));
			if(old == null){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			BeanUtils.copyProperties(patient, old);
			patient = old;
		}
		LOG.info(patient.getId().toString());
		Patient result = patientService.save(patient);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

	@Override
	public Response getPatients() {
		try {
			// patientService.savePatient(getTestPatient());
			return Response.ok(convertPatientListToSummaryList(patientService.getAll())).build();
		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	private Consultation getTestConsultation(Date date){
		Consultation consult = new Consultation();
		consult.setDate(date);
		return consult;
	}
	
	private Patient getTestPatient() throws ParseException {

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
		
		String startDateString = "2013-03-26"; 
		String futureDate = "2099-03-26"; 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
		patient.addConsultation(getTestConsultation(df.parse(startDateString)));
		patient.addConsultation(getTestConsultation(df.parse(futureDate)));

		HeredoFamilyBackground familyBackground = new HeredoFamilyBackground();
		familyBackground.addDisease(null);

		patient.setHereditaryFamilyBackground(familyBackground);

		return patient;
	}
	
	public List<PatientSummary> convertPatientListToSummaryList(List<Patient> patients){
		List<PatientSummary> summary = new ArrayList<PatientSummary>();
		for(Patient patient : patients){
			PatientSummary s = new PatientSummary();
			s.setId(patient.getId());
			s.setFirstName(patient.getFirstName());
			s.setLastName(patient.getLastName());
			s.setLastConsultation(patientService.getLastConsultation(patient.getId()).getDate());
			s.setNextConsultation(patientService.getNextConsultation(patient.getId()).getDate());
			summary.add(s);
		}
		return summary;
	}

	@Override
	public Response deletePatient(String patientId) {
		try {
			if (patientId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			patientService.deleteById(Long.parseLong(patientId));
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
			Patient old = patientService.getById(patient.getId());
			BeanUtils.copyProperties(patient, old);
			patient = old;
		}

		Patient result = patientService.save(patient);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

	@Override
	public Response getPatientFamilyBackground(String patientId) {
		try {
			if (patientId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			Patient patient = patientService.getById(Long.parseLong(patientId));
			HeredoFamilyBackground background = patient.getHereditaryFamilyBackground();
			return Response.ok(background).build();

		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

}