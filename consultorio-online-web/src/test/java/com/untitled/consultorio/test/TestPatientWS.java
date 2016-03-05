package com.untitled.consultorio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.consultorio.webapp.ws.controller.api.PatientWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml" })
public class TestPatientWS {

	@Autowired
	PatientWebService patientWebService;
	
	@Test
	public void test() throws Exception{
		EmbeddedJetty.main(null);
		System.err.println(patientWebService.getPatients());
	}
}
