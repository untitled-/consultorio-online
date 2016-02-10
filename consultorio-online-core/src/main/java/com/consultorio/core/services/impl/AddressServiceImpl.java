package com.consultorio.core.services.impl;

import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.repo.AddressRepository;
import com.consultorio.core.exceptions.AddressNotFoundException;
import com.consultorio.core.exceptions.ElementNotPersistedException;
import com.consultorio.core.services.AddressService;

@Component
@Qualifier("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepo;
	
	@Override
	public List<Address> getAll() {
		Iterable<Address> addresses = addressRepo.findAll();
		if(addresses!=null){
			return IteratorUtils.toList(addresses.iterator()); 
		}
		else{
			throw new AddressNotFoundException();
		}
	}

	@Override
	public Address save(Address address) {
		try{
			Address result = addressRepo.save(address);
			Assert.notNull(result);
			return result;
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public Address getById(Long id) {
		try{
			Assert.notNull(id, "Address id is null");
			Address result = addressRepo.findOne(id);
			
			return result;
			
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try{
			addressRepo.delete(id);
			
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void delete(Address address) {
		try{
			addressRepo.delete(address);
			
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public void deleteAll() {
		try{
			addressRepo.deleteAll();
			
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

	@Override
	public Boolean isExist(Address address) {
		try{
			Address result = addressRepo.findOne(address.getId());
			if(result!=null){
				return true;
			}
			else{
				return false;
			}
			
		}catch(Exception e){
			throw new ElementNotPersistedException(e);
		}
	}

}
