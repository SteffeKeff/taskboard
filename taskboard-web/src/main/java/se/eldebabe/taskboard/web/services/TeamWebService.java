package se.eldebabe.taskboard.web.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.gson.Gson;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.services.TeamService;
import se.eldebabe.taskboard.data.services.UserService;

@Path("teams")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})
public class TeamWebService{

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static TeamService teamService;
	private static UserService userService;

	com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	Gson gson = new Gson();

	static {
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		teamService = context.getBean(TeamService.class);
		userService = context.getBean(UserService.class);

	}

	@GET
	@Path("/id/{id}")
	public Response getTeamById(@PathParam("id") final Long id)
			throws JsonGenerationException, JsonMappingException, IOException{
		Team team = teamService.findById(id);

		if(null != team) {

			return Response.ok(mapper.writeValueAsString(team)).build();
		} else {
			return Response.noContent().build();
		}

	}

	@GET
	@Path("{name}")
	public Response getTeamByName(@PathParam("name") final String name)
			throws JsonGenerationException, JsonMappingException, IOException{
		Team team = teamService.findTeamByName(name);

		if(null != team) {

			return Response.ok(mapper.writeValueAsString(team)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/id/{id}/workitems")
	public Response getAllWorkItemsInTeam(@PathParam("id") final Long id)
			throws JsonGenerationException, JsonMappingException, IOException{
		Team team = teamService.findById(id);
		HashSet<WorkItem> workItems = new HashSet<>();

		if(null != team) {
			for(User user : team.getUsers()){
				workItems.addAll(user.getWorkItems());
			}
			return Response.ok(mapper.writeValueAsString(workItems)).build();
		}else{
			return Response.noContent().build();
		}
	}

	@POST
	public Response saveTeam(String jSon) throws JsonParseException, JsonMappingException, IOException {

		Team team = mapper.readValue(jSon, Team.class);

		teamService.saveTeam(team);
		return Response.ok(mapper.writeValueAsString(team)).build();
	}

	@PUT
	@Path("/id/{id}/{userId}")
	public Response addUsersToTeam(@PathParam("id") final Long id, @PathParam("userId") final String userID)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException {
		User userToBeAdded = userService.findUser(userID);
		userToBeAdded.setTeam(teamService.findById(id));
		userService.saveUser(userToBeAdded);
		Team updatedTeam = teamService.findById(id);
		return Response.ok(mapper.writeValueAsString(updatedTeam)).build();

	}

	@GET
	public Response getAllTeams() throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException{

		List<Team> teams = teamService.findAllTeams();
		if (teams == null || teams.size() == 0) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(mapper.writeValueAsString(teams)).build();
		}
	}

	@DELETE
	@Path("{name}")
	public final Response deleteTeamByName(@PathParam("name") final String name)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException{
		Team teamWithUsers = teamService.findTeamByName(name);
		List<User> usersInTeam = (List<User>) teamWithUsers.getUsers();

		for (User user : usersInTeam){
			user.setTeam(null);
			userService.saveUser(user);
		}

		List<Team> teams = teamService.deleteByName(name);

		if (teams != null){
			return Response.ok(mapper.writeValueAsString(teams.get(0))).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/id/{id}")
	public final Response deleteTeamById(@PathParam("id") final Long id)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException{
		Team teamWithUsers = teamService.findById(id);
		List<User> usersInTeam = (List<User>) teamWithUsers.getUsers();
		for(User user : usersInTeam){
			user.setTeam(null);
			userService.saveUser(user);
		}

		Team team = teamService.delete(id);
		if(team != null){
			return Response.ok(mapper.writeValueAsString(team)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/id/{id}/users/{userId}")
	public final Response removeUserFromTeam(@PathParam("id") final Long id, @PathParam("userId") final String userId)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException{

		Team team = teamService.findById(id);
		User user = userService.findUser(userId);

		if (team != null && user != null){
			user.setTeam(null);
			userService.saveUser(user);
			team = teamService.findById(id);
			return Response.ok(mapper.writeValueAsString(team)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

}
