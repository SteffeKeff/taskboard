package se.eldebabe.taskboard.web.services;


import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cedarsoftware.util.io.JsonWriter;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.services.UserService;

@Path("users")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
public class UserWebService{
	
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static UserService userService;
	com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	
	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		userService = context.getBean(UserService.class);
	}
	
	@Context
	public UriInfo uriInfo;

	@POST
	public Response createUser(final String json) throws JsonParseException, JsonMappingException, IOException  {
		
		User user = mapper.readValue(json, User.class);
		user = userService.saveUser(user);
		
		if(null != user){
			return Response.ok(mapper.writeValueAsString(user)).header("Location", uriInfo.getPath() + "/" + user.getUserId().toString()).build();
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
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("username")
	public Response searchUserByUserName(@QueryParam("username") final String userName) throws JsonGenerationException, JsonMappingException, IOException
	{
		User user = userService.findByUserName(userName);
		if(null != user){
			return Response.ok(mapper.writeValueAsString(user)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("firstname")
	public Response searchUserByFirstName(@QueryParam("firstname") final String firstName) throws JsonGenerationException, JsonMappingException, IOException
	{
		ArrayList<User> user = (ArrayList<User>) userService.findByFirstname(firstName);
		
		if(null != user | !user.isEmpty()){
			return Response.ok(mapper.writeValueAsString(user)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("lastname")
	public Response searchUserByLastName(@QueryParam("lastname") final String lastName) throws JsonGenerationException, JsonMappingException, IOException
	{
		ArrayList<User> user = (ArrayList<User>) userService.findByLastname(lastName);
		if(null != user | !user.isEmpty()){
			return Response.ok(mapper.writeValueAsString(user)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String userId) throws com.fasterxml.jackson.core.JsonGenerationException, com.fasterxml.jackson.databind.JsonMappingException, IOException {
		
		User user = userService.findUser(userId);
		if(user != null){
			userService.deleteUser(user.getId());
			return Response.ok(mapper.writeValueAsString(user)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final String userId, final String json) throws com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException, IOException {
		User user = mapper.readValue(json, User.class);
		user.setUserId(userId);
		user = userService.updateUser(user);
		
		if(user != null){
			return Response.ok(JsonWriter.toJson(user)).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
		
	}
	
}
