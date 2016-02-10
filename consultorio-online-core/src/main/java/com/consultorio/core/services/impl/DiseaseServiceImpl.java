package com.consultorio.core.services.impl;

import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.consultorio.core.dataaccess.entity.Disease;
import com.consultorio.core.dataaccess.repo.DiseaseRepository;
import com.consultorio.core.exceptions.ElementNotFoundException;
import com.consultorio.core.exceptions.ElementNotPersistedException;
import com.consultorio.core.services.DiseaseService;

@Component
@Qualifier("diseaseService")
public class DiseaseServiceImpl implements DiseaseService {

	@Autowired
	DiseaseRepository repository;
	
	@Override
	public List<Disease> getAll() {
		Iterable<Disease> results = repository.findAll();
		if (results != null) {
			return IteratorUtils.toList(results.iterator());
		} else {
			throw new ElementNotFoundException();
		}
	}

	@Override
	public Disease save(Disease newElement) {
		try {
			Disease result = repository.save(newElement);
			Assert.notNull(result);
			return result;
		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public Disease getById(Long id) {
		try {
			Assert.notNull(id, "Patient id is null");
			Disease result = repository.findOne(id);

			return result;

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			repository.delete(id);

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void delete(Disease element) {
		try {
			repository.delete(element);

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			repository.deleteAll();

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public Boolean isExist(Disease element) {
		try {
			Disease result = repository.findOne(element.getId());
			if (result != null) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

}
