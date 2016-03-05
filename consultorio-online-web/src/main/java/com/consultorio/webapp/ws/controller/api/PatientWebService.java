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

import com.consultorio.core.dataaccess.entity.Patient;

/**
 * 
 * @author lessonslab.com
 * This is interface for the employee services
 *
 */
@Path("/patient")
@WebService(name="patientService", targetNamespace="http://www._untitled.com/rest/consultorio-online")
public interface PatientWebService 
{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/")
	public Response getPatients();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Response getPatient(@PathParam("id") String patientId);
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{id}/familyBackground")
	public Response getPatientFamilyBackground(@PathParam("id") String patientId);
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
	@Path("/")
	public Response savePatient(Patient patient);
	
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
	@Path("/{id}")
	public Response updatePatient(@PathParam("id") String patientId, Patient patient);
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Response deletePatient(@PathParam("id") String patientId);
	
}