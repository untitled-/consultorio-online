package com.consultorio.core.services.impl;

import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Patient;
import com.consultorio.core.dataaccess.repo.PatientRepository;
import com.consultorio.core.exceptions.ElementNotPersistedException;
import com.consultorio.core.exceptions.PatientNotFoundException;
import com.consultorio.core.services.AddressService;
import com.consultorio.core.services.HeredoFamilyBackgroundService;
import com.consultorio.core.services.PatientService;

@Component
@Qualifier("patientService")
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository patientRepo;
	@Autowired
	AddressService addressService;
	@Autowired
	HeredoFamilyBackgroundService backgroundService;

	@Override
	public List<Patient> getAll() {
		Iterable<Patient> patients = patientRepo.findAll();
		if (patients != null) {
			return IteratorUtils.toList(patients.iterator());
		} else {
			throw new PatientNotFoundException();
		}
	}

	@Override
	public Patient save(Patient patient) {
		try {
			Address address = patient.getAddress();
			if(address!=null){
				address = addressService.save(address);	
			}

			patient.setAddress(address);
			Patient result = patientRepo.save(patient);
			Assert.notNull(result);
			return result;
		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public Patient getById(Long id) {
		try {
			Assert.notNull(id, "Patient id is null");
			Patient result = patientRepo.findOne(id);

			return result;

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			patientRepo.delete(id);

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void delete(Patient patient) {
		try {
			patientRepo.delete(patient);

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}

	}

	@Override
	public Boolean isExist(Patient patient) {
		try {
			Patient result = patientRepo.findOne(patient.getId());
			if (result != null) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			patientRepo.deleteAll();

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}

	}

}
