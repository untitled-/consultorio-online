package com.consultorio.core.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Disease;
import com.consultorio.core.dataaccess.entity.Gender;
import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;
import com.consultorio.core.dataaccess.entity.Patient;
import com.consultorio.core.services.AddressService;
import com.consultorio.core.services.DiseaseService;
import com.consultorio.core.services.PatientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-main-context.xml")
@ActiveProfiles("application")
public class PatientServiceTest {
	/**
	 * Service to manage Patients. Created, Read, Updated and Delete.
	 */
	@Autowired
	PatientService partientService;

	@Autowired
	AddressService addressService;
	
	@Autowired
	DiseaseService diseaseService;

	@Before
	public void setUp() throws Exception {
		assertNotNull("The person service is null", partientService);
	}

	@After
	public void tearDown() throws Exception {
		partientService.deleteAll();
		addressService.deleteAll();
		diseaseService.deleteAll();
	}

	/**
	 * Test creating a user with no background nor address (empty references to
	 * external types of entities)
	 */
	@Test
	public void testCreatePatientNoBackground() {
		Patient result = partientService.save(getTestPatient(null, null));
		assertNotNull("The patient is null", result);
		assertNull("The background is not null", result.getHereditaryFamilyBackground());
	}

	/**
	 * Test creating a patient with a background without diseases
	 */
	@Test
	public void testCreatePatientWithEmptyBackground() {
		Patient result = partientService.save(getTestPatient(null, getTestHeredoBackground(null)));
		assertNotNull("The patient is null", result);
		assertNotNull("The background is null", result.getHereditaryFamilyBackground());
	}
	
	/**
	 * Test creating a patient with a background with one disease
	 */
	@Test
	public void testCreatePatientWithBackgroundOneDisease() {
		Disease disease = diseaseService.save(getTestDisease());
		Patient result = partientService.save(getTestPatient(null, getTestHeredoBackground(disease)));
		assertNotNull("The patient is null", result);
		assertNotNull("The background is null", result.getHereditaryFamilyBackground());
	}
	
	@Test
	public void testUpdatePatientWithBackgroundOneDisease() {
		Disease disease = diseaseService.save(getTestDisease());
		Patient result = partientService.save(getTestPatient(null, getTestHeredoBackground(disease)));
		assertNotNull("The patient is null", result);
		assertNotNull("The background is null", result.getHereditaryFamilyBackground());
		
		final String newName = "Pedro1";
		final Long oldId = result.getId();
		result.setFirstName(newName);
		result = partientService.save(result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), newName);
		assertEquals("The patient's id is not the expected", result.getId(), oldId);
		assertEquals("The total patients number is wrong", 1, partientService.getAll().size());
		assertNotNull("The background is null", result.getHereditaryFamilyBackground());
		assertFalse("The disease is null", result.getHereditaryFamilyBackground().getDiseases().isEmpty());
		assertEquals("The total diseases number is wrong", 1, diseaseService.getAll().size());
	}

	/**
	 * Test creating a user with no background nor address (empty references to
	 * external types of entities)
	 */
	@Test
	public void testGetPatient() {
		Patient result = partientService.save(getTestPatient(null, null));
		result = partientService.getById(result.getId());
		assertNotNull("The patient is null", result);
		assertNull("The background is not null", result.getHereditaryFamilyBackground());
	}

	/**
	 * Test retrieving all the patients.
	 */
	@Test
	public void testGetAllPatients() {
		Patient result = partientService.save(getTestPatient(null, null));
		Patient result2 = partientService.save(getTestPatient(null, null));
		assertNotNull("The patient was not saved", result);
		assertNotNull("The patient was not saved", result2);
		List<Patient> patients = partientService.getAll();
		assertNotNull(patients);
		assertEquals("The number of found patients is wrong", 2, patients.size());
	}

	/**
	 * Test updating a patient.
	 */
	@Test
	public void testUpdatePatientNoBackground() {
		Patient testPatient = getTestPatient(null, null);
		Patient result = partientService.save(testPatient);
		assertNotNull("The patient is null", result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), testPatient.getFirstName());

		final String newName = "Pedro1";
		final Long oldId = result.getId();
		result.setFirstName(newName);
		result = partientService.save(result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), newName);
		assertEquals("The patient's id is not the expected", result.getId(), oldId);
		assertEquals("The total patients number is wrong", 1, partientService.getAll().size());
		assertNull("The background is not null", result.getHereditaryFamilyBackground());
	}

	/**
	 * Test updating a patient with a new address. The update should not create
	 * a new address, only use the one already exists.
	 */
	@Test
	public void testUpdatePatientWithAddress() {
		Address address = getTestAddress();
		Patient testPatient = getTestPatient(address, null);
		Patient result = partientService.save(testPatient);
		assertNotNull("The patient is null", result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), testPatient.getFirstName());

		final String newName = "Pedro1";
		final Long oldId = result.getId();
		result.setFirstName(newName);
		result = partientService.save(result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), newName);
		assertEquals("The patient's id is not the expected", result.getId(), oldId);
		assertEquals("The total patients number is wrong", 1, partientService.getAll().size());
		assertFalse("The addresses should not be empty",addressService.getAll().isEmpty());
		assertEquals("The number of addresses is wrong",1,addressService.getAll().size());
		assertNull("The background is not null", result.getHereditaryFamilyBackground());
	}
	
	/**
	 * Test updating a patient with an already existing address. The update should not create
	 * a new address, only use the one already exists.
	 */
	@Test
	public void testUpdatePatientWithAlreadySavedAddress() {
		Address address = getTestAddress();
		address = addressService.save(address);
		
		Patient testPatient = getTestPatient(address, null);
		System.err.println(address);
		Patient result = partientService.save(testPatient);
		assertNotNull("The patient is null", result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), testPatient.getFirstName());

		final String newName = "Pedro1";
		final Long oldId = result.getId();
		result.setFirstName(newName);
		result = partientService.save(result);
		assertEquals("The patient's first name is not the expected", result.getFirstName(), newName);
		assertEquals("The patient's id is not the expected", result.getId(), oldId);
		assertEquals("The total patients number is wrong", 1, partientService.getAll().size());
		assertFalse("The addresses should not be empty",addressService.getAll().isEmpty());
		assertEquals("The number of addresses is wrong",1,addressService.getAll().size());
		assertNull("The background is not null", result.getHereditaryFamilyBackground());
	}

	public Patient getTestPatient(Address address, HeredoFamilyBackground familyBackground) {

		Patient patient = new Patient();
		patient.setFirstName("Pedro" + System.currentTimeMillis());
		patient.setGender(Gender.MALE);
		patient.setAddress(address);

		patient.setHereditaryFamilyBackground(familyBackground);

		return patient;
	}

	public HeredoFamilyBackground getTestHeredoBackground(Disease disease) {
		HeredoFamilyBackground familyBackground = new HeredoFamilyBackground();
		familyBackground.addDisease(disease);
		return familyBackground;
	}

	public Disease getTestDisease() {
		Disease disease = new Disease();
		disease.setCode("D123456789");
		disease.setName("Diabetes");
		disease.setType("DM1");
		return disease;
	}

	public Address getTestAddress() {
		Address address = new Address();
		address.setStreet("4701 Staggerbrush Rd");
		address.setCity("Austin");
		address.setState("Texas");
		return address;
	}

}
