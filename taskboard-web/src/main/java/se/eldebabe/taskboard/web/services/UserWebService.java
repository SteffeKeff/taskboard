package se.eldebabe.taskboard.web.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("users")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
public class UserWebService{

	@GET
	public Response getAllUsers()
	{
		return null;
	}
	
	@PUT
	public Response addUser() {

		return null;
	}
	
	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final String userId)
	{
		return null;
	}
	
	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String userId) {
		return null;
	}
	
	@POST
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final String userId)  {
		
		return null;
	}
	
}
