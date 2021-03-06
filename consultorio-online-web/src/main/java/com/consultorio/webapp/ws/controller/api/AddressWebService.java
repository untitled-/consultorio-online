package com.consultorio.webapp.ws.controller.api;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.consultorio.core.dataaccess.entity.Address;
import com.consultorio.core.dataaccess.entity.Patient;

/**
 * 
 * @author lessonslab.com This is interface for the employee services
 *
 */
@Path("/address")
@WebService(name = "addressService", targetNamespace = "http://www._untitled.com/rest/consultorio-online")
public interface AddressWebService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/")
	public Response getAddresses();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response getAddress(@PathParam("id") String addressId);

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/")
	public Response saveAddress(Address address);

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/{id}")
	public Response updateAddress(@PathParam("id") String addressId, Address address);

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response deleteAddress(@PathParam("id") String addressId);

}