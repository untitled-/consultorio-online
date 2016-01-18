package com.consultorio.webapp.ws.controller.impl;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.services.AddressService;
import com.consultorio.webapp.ws.controller.api.AddressWebService;

@Component
public class AddressWebServiceImpl implements AddressWebService {
	
	private static Logger LOG = LoggerFactory.getLogger(AddressWebServiceImpl.class);

	@Autowired
	AddressService addressService;

	@PostConstruct
	public void initIt() throws Exception {
		
	}
	
	@Override
	public Response getAddresses() {
		try {
			return Response.ok(addressService.getAllAddresses()).build();
		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@Override
	public Response getAddress(String addressId) {
		try {
			if (addressId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			Address result = addressService.getAddressById(Long.parseLong(addressId));
			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response saveAddress(Address address) {
		if (address == null) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (address.getId() != null) {
			Address old = addressService.getAddressById(address.getId());
			BeanUtils.copyProperties(address, old);
			address = old;
		}

		Address result = addressService.saveAddress(address);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

	@Override
	public Response updateAddress(String addressId, Address address) {
		if (address == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (addressId != null) {
			address.setId(Long.parseLong(addressId));
			Address old = addressService.getAddressById(Long.parseLong(addressId));
			if(old == null){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			BeanUtils.copyProperties(address, old);
			address = old;
		}
		LOG.info(address.getId().toString());
		Address result = addressService.saveAddress(address);
		return Response.ok(result).status(Response.Status.CREATED).build();
	}

	@Override
	public Response deleteAddress(String addressId) {
		try {
			if (addressId == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			addressService.deleteAddressById(Long.parseLong(addressId));
			return Response.ok().build();

		} catch (Exception e) {
			LOG.error("Error in the service", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

}
