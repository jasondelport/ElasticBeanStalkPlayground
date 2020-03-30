package com.jasondelport.jaxb.demo;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus bus() {
		SpringBus springBus = new SpringBus();
		LoggingFeature logFeature = new LoggingFeature();
		logFeature.setPrettyLogging(true);
		logFeature.initialize(springBus);
		springBus.getFeatures().add(logFeature);
		return springBus;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// Apache CXF -- SpringBoot
	// JAX-WS (different from JAX-RS)
	// https://cxf.apache.org/docs/springboot.html

	// JAX-WS endpoint
	// http://localhost:8080/services/ws/
	// http://localhost:8080/services/ws/ReceiveXml?wsdl
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus(), new ReceiveXml());
		endpoint.publish("/ws");
		return endpoint;
	}

	@Autowired
	private RestService restService;

	// JAX-RS endpoint
	// http://localhost:8080/services/rest/people
	// http://localhost:8080/services/rest/swagger.json
	// http://localhost:8080/services/rest/api-docs/?url=/services/rest/swagger.json
	@Bean
	public Server server() {

		final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setProvider(new JacksonJsonProvider());
		factory.setServiceBean(restService);
		factory.setBus(bus());
		factory.setAddress("/rest");
		factory.getFeatures().add(new Swagger2Feature());
		return factory.create();
	}

}
