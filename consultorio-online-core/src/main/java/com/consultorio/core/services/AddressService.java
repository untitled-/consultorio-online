package com.consultorio.core.services;

import java.util.List;

import com.consultorio.core.dataaccess.entity.Address;

public interface AddressService {
	List<Address> getAllAddresses();
	Address saveAddress(Address address);
	Address getAddressById(Long id);
	void deleteAddressById(Long id);
	void deleteAddress(Address address);
	void deleteAllAddresss();
	Boolean isAddressExist(Address address);
	
}
