package com.consultorio.core.services;

import java.util.List;

import com.consultorio.core.dataaccess.entity.Patient;

public interface PatientService {
	List<Patient> getAllPatients();
	Patient savePatient(Patient patient);
	Patient getPatientById(Long id);
	void deletePatientById(Long id);
	void deletePatient(Patient patient);
	void deleteAllPatients();
	Boolean isPatientExist(Patient patient);
	
}
