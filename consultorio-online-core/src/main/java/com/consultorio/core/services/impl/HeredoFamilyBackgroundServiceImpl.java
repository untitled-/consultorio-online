package com.consultorio.core.services.impl;

import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;
import com.consultorio.core.dataaccess.repo.HeredoFamilyBackgroundRepository;
import com.consultorio.core.exceptions.ElementNotFoundException;
import com.consultorio.core.exceptions.ElementNotPersistedException;
import com.consultorio.core.services.HeredoFamilyBackgroundService;


@Component
@Qualifier("heredoService")
public class HeredoFamilyBackgroundServiceImpl implements HeredoFamilyBackgroundService {

	@Autowired
	HeredoFamilyBackgroundRepository repository;
	
	@Override
	public List<HeredoFamilyBackground> getAll() {
		Iterable<HeredoFamilyBackground> backgrounds = repository.findAll();
		if (repository != null) {
			return IteratorUtils.toList(backgrounds.iterator());
		} else {
			throw new ElementNotFoundException();
		}
	}

	@Override
	public HeredoFamilyBackground save(HeredoFamilyBackground background) {
		try {
			HeredoFamilyBackground result = repository.save(background);
			Assert.notNull(result);
			return result;
		} catch (Exception e) {
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public HeredoFamilyBackground getById(Long id) {
		try {
			Assert.notNull(id, "Patient id is null");
			HeredoFamilyBackground result = repository.findOne(id);

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
	public void delete(HeredoFamilyBackground patient) {
		try {
			repository.delete(patient);

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
	public Boolean isExist(HeredoFamilyBackground background) {
		try {
			HeredoFamilyBackground result = repository.findOne(background.getId());
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
