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

import com.consultorio.core.dataaccess.entity.HeredoFamilyBackground;

/**
 * 
 * @author lessonslab.com This is interface for the employee services
 *
 */
@Path("/familyBackground")
@WebService(name = "familyBackgroundService", targetNamespace = "http://www._untitled.com/rest/consultorio-online")
public interface FamilyBackgroundWebService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/")
	public Response getHeredoFamilyBackgrounds();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response getHeredoFamilyBackground(@PathParam("id") String familyBackgroundId);

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/")
	public Response saveHeredoFamilyBackground(HeredoFamilyBackground familyBackground);

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/{id}")
	public Response updateHeredoFamilyBackground(@PathParam("id") String familyBackgroundId, HeredoFamilyBackground familyBackground);

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response deleteHeredoFamilyBackground(@PathParam("id") String familyBackgroundId);

}