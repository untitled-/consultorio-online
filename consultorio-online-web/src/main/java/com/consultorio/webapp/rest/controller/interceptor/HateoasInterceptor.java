package com.consultorio.webapp.rest.controller.interceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.consultorio.webapp.ws.controller.impl.AddressWebServiceImpl;

@Component
public class HateoasInterceptor extends AbstractPhaseInterceptor<Message> {

	private static Logger LOG = LoggerFactory.getLogger(AddressWebServiceImpl.class);

	public HateoasInterceptor() {

		super(Phase.POST_PROTOCOL); // Put this interceptor in this phase
		System.err.println("Start");
	}

	public void handleMessage(Message msg) throws Fault {
		
		Map<String, List> headers = (Map<String, List>) msg.get(Message.PROTOCOL_HEADERS);
		try {

			headers.put("Server", Collections.singletonList("Your-mom"));
			
			msg.put(Message.PROTOCOL_HEADERS, headers);
		} catch (Exception ce) {
			throw new Fault(ce);
		}

		LOG.info("Intercepting the response");
		LOG.info(msg.toString());


		// process the message
	}
}
