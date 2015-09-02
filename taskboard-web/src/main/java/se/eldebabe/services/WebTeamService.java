package se.eldebabe.services;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("teams")
public class WebTeamService {
	
	@GET
	public Response getAllTeams()
	{
		return null;
	}
	
	@PUT
	public Response addUser() {
		return null;

	}
	
	@DELETE
	@Path("{name}")
	public final Response deleteUser(@PathParam("name") final String name) {
		return null;
	}

}
