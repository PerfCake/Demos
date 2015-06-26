package org.perfcake.demos.czechtest2015.jaxrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class PlusService {
	
	@GET
	@Path("/{i}")
	@Consumes("text/plain")
	@Produces("text/plain")
	public int plus(@PathParam("i") int i){
		return i+1;
	}
}
