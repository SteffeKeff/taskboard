package se.eldebabe.taskboard.web.services;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.services.UserService;
import se.eldebabe.taskboard.data.services.WorkItemService;

@Path("users")
@Produces({ javax.ws.rs.core.MediaType.APPLICATION_JSON })
@Consumes({ javax.ws.rs.core.MediaType.APPLICATION_JSON })
public class UserWebService {

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static UserService userService;
	private static WorkItemService workItemService;
	com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

	static {
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		userService = context.getBean(UserService.class);
		workItemService = context.getBean(WorkItemService.class);
	}

	@Context
	public UriInfo uriInfo;

	@POST
	public Response createUser(final String json) throws JsonParseException, JsonMappingException, IOException {

		User user = mapper.readValue(json, User.class);
		user = userService.saveUser(user);

		if (null != user) {
			return Response.ok(mapper.writeValueAsString(user))
					.header("Location", uriInfo.getPath() + "/id/" + user.getUserId().toString()).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}

	}

	@GET
	@Path("/id/{userId}")
	public Response getUser(@PathParam("userId") final String userId)
			throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.findUser(userId);
		if (null != user) {
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	public Response searchUserByUserName(@QueryParam("username") final String userName)
			throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.findByUserName(userName);
		if (null != user) {
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("firstname")
	public Response searchUserByFirstName(@QueryParam("firstname") final String firstName)
			throws JsonGenerationException, JsonMappingException, IOException {
		ArrayList<User> user = (ArrayList<User>) userService.findByFirstname(firstName);

		if (null != user | !user.isEmpty()) {
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("lastname")
	public Response searchUserByLastName(@QueryParam("lastname") final String lastName)
			throws JsonGenerationException, JsonMappingException, IOException {
		ArrayList<User> user = (ArrayList<User>) userService.findByLastname(lastName);
		if (null != user | !user.isEmpty()) {
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("pages")
	public final Response getAllUsers(@DefaultValue("0") @QueryParam("page") final int page, @DefaultValue("0") @QueryParam("size") final int size)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException {
		ArrayList<User> users = new ArrayList<>();
		
		if (!users.isEmpty()) {
			if (page > 0 && size > 0) {
				Iterable<User> userPages;
				userPages = userService.findAllUsers(page, size);
				for(User user : userPages){
					users.add(user);
				}
				return Response.ok(mapper.writeValueAsString(users)).build();
			}else{
				Iterable<User> userPages;
				userPages = userService.findAllUsers();
				for(User user : userPages){
					users.add(user);
				}
				return Response.ok(mapper.writeValueAsString(users)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String userId)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException {

		User user = userService.findUser(userId);
		if (user != null) {
			userService.deleteUser(user.getId());
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final String userId, final String json)
			throws com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException,
			IOException {
		User user = mapper.readValue(json, User.class);
		user.setUserId(userId);
		user = userService.saveUser(user);

		if (user != null) {
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("{userId}/workitems")
	public Response addWorkItemToUser(@PathParam("userId") final String userId, final String json)
			throws com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException,
			IOException {
		User user = userService.findUser(userId);

		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);

		Long id = jobj.get("id").getAsLong();
		WorkItem workItem = workItemService.findWorkItem(id);

		if (null != user && null != workItem) {
			user.addWorkItem(workItem);
			user = userService.saveUser(user);
			return Response.ok(mapper.writeValueAsString(user)).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("{userId}/workitems")
	public Response getAllWorkItemsFromUser(@PathParam("userId") final String userId)
			throws com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException,
			IOException {
		User user = userService.findUser(userId);

		if (null != user) {
			return Response.ok(mapper.writeValueAsString(user.getWorkItems())).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

}
