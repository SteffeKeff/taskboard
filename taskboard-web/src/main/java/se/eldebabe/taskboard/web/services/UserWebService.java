package se.eldebabe.taskboard.web.services;


import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.services.UserService;

@Path("users")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
public final class UserWebService{
	
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static UserService userService = new UserService();
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		userService = context.getBean(UserService.class);
	}
	
	@Context
	public UriInfo uriInfo;

	@GET
	public Response getAllUsers()
	{
		return null;
	}
	
	@POST
	public final Response addUser(final String json) throws JsonParseException, JsonMappingException, IOException{

		User user = mapper.readValue(json, User.class);
		
		user = userService.saveUser(user);
		if(null != user){			
			return Response.ok(mapper.writeValueAsString(user)).header("Location", uriInfo.getPath() + "/" + user.getId().toString()).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final String userId) throws JsonGenerationException, JsonMappingException, IOException
	{
		User user = userService.findUser(userId);
		if(null != user){
			return Response.ok(mapper.writeValueAsString(user)).build();
		}else{
			return Response.noContent().build();
		}
	}
	
	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String userId) {
		return null;
	}
	
	@PUT
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final String userId)  {
		
		return null;
	}
	
}
