package com.consultorio.webapp.ws.controller;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.consultorio.core.dataaccess.entity.Patient;

/**
 * 
 * @author lessonslab.com
 * This is interface for the employee services
 *
 */
@Path("/")
@WebService(name="employeeService", targetNamespace="http://www.lessonslab.com/cxf-rest/example")
public interface CxfRestService 
{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/getemployeedetail")
	public Response getEmployeeDetail(@QueryParam("employeeId") String employeeId);
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
	@Path("/getemployeedetail")
	public Response savePatient(Patient patient);
	
}