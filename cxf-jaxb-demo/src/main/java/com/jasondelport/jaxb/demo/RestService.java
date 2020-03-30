package com.jasondelport.jaxb.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/")
@Api
@Component
public class RestService {

	@GET
	@Path("/")
	public String getRoot() {
		return "REST API is Available";
	}

	@GET
	@Path("/people")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds People",
			notes = "Returns all people",
			response = Person.class,
			responseContainer = "List")
	public Collection<Person> getPeople() {
		List<Person> list = new ArrayList<>();
		list.add(new Person("John"));
		return list;
	}
}