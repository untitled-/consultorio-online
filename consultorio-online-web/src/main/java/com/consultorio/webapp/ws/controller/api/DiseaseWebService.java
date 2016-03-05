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

import com.consultorio.core.dataaccess.entity.Disease;

/**
 * 
 * @author lessonslab.com This is interface for the employee services
 *
 */
@Path("/disease")
@WebService(name = "diseaseService", targetNamespace = "http://www._untitled.com/rest/consultorio-online")
public interface DiseaseWebService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/")
	public Response getDiseases();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response getDisease(@PathParam("id") String diseaseId);

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/")
	public Response saveDisease(Disease disease);

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ "application/xml", "application/json", "application/x-www-form-urlencoded" })
	@Path("/{id}")
	public Response updateDisease(@PathParam("id") String diseaseId, Disease disease);

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response deleteDisease(@PathParam("id") String diseaseId);

}