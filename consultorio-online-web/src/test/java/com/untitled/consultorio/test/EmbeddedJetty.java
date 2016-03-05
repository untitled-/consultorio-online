package com.untitled.consultorio.test;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.consultorio.webapp.ws.controller.impl.AddressWebServiceImpl;

public class EmbeddedJetty {

	private static Logger logger = LoggerFactory.getLogger(AddressWebServiceImpl.class);
    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/consultorio-online-web";
    private static final String CONFIG_LOCATION = "eu.kielczewski.example.config";
    private static final String MAPPING_URL = "/*";
    private static final String DEFAULT_PROFILE = "dev";

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(getPortFromArgs(args));
    }

    private static int getPortFromArgs(String[] args) {
        if (args!=null && args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        logger.debug("No server port configured, falling back to {}", DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    private void startJetty(int port) throws Exception {
        logger.debug("Starting server at port {}", port);
        Server server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));
        server.start();
        logger.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase("webapp");
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
    	AbstractApplicationContext ctx = 
    		    new ClassPathXmlApplicationContext("/WEB-INF/app-context.xml");
    	XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setParent(ctx);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        context.refresh();
        return context;
    }

}