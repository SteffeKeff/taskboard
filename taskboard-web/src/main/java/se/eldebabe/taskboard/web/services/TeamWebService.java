package se.eldebabe.taskboard.web.services;

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

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.eldebabe.taskboard.data.models.*;
import se.eldebabe.taskboard.data.services.TeamService;
import se.eldebabe.taskboard.data.services.UserService;

@Path("teams")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
@Consumes({javax.ws.rs.core.MediaType.APPLICATION_JSON})

public class TeamWebService {
	
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static TeamService teamService;
	private static UserService userService;
	Gson gson = new Gson();
	
	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		teamService = context.getBean(TeamService.class);
		
	}
	
	@GET
	@Path("/id/{id}")
	public Response getTeamById(@PathParam("id") final Long id) {
		Team team = teamService.findById(id);
		
		if(null != team){
			return Response.ok(JsonWriter.toJson(team)).build();
		}else{
			return Response.noContent().build();
		}
		
	}
	
	@GET
	@Path("{name}")
	public Response getTeamByName(@PathParam("name") final String name) {
		Team team = teamService.findTeamByName(name);
		
		if(null != team){
			return Response.ok(JsonWriter.toJson(team)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	public Response saveTeam(String jSon){
		
		JsonObject jo = new Gson().fromJson(jSon, JsonObject.class);
		String teamName = jo.get("teamname").getAsString();
		

		Team team = new Team(teamName);
		teamService.saveTeam(team);
		return Response.ok(JsonWriter.toJson(team)).build();
	}
	
	@POST
	@Path("/id/{id}")
	public Response saveTeamWithUsers(@PathParam("id") final Long id,  final String userID){
		
		User userToBeAdded = userService.findUser(userID);
		
		userToBeAdded.setTeam(teamService.findById(id));
		
		Team updatedTeam = teamService.findById(id);
		
        return Response.ok(JsonWriter.toJson(updatedTeam)).build();

	}
	
	@GET
	public Response getAllTeams()
	{
	
		List<Team> teams = teamService.findAllTeams();
		if(teams == null || teams.size() == 0) {
			return Response.status(Status.NOT_FOUND).build();
		}else{
			teams.forEach(System.out::println);
		}
		
		return Response.ok(teams.get(0).toString()).build();
	}
	
	@PUT
	public Response addTeam() {
		return null;

	}
	
	@DELETE
	@Path("{name}")
	public final Response deleteTeamByName(@PathParam("name") final String name) {

		List<Team> teams =teamService.deleteByName(name);

		if(teams != null){
			return Response.ok(JsonWriter.toJson(teams.get(0))).build();
		}
		return Response.status(Status.NOT_FOUND).build();

		
	}
	
	@DELETE
	@Path("/id/{id}")
	public final Response deleteTeamById(@PathParam("id") final Long id) {
		
		Team team = teamService.delete(id);
		
		if(team != null){
			return Response.ok(JsonWriter.toJson(team)).build();
		}
		return Response.status(Status.NOT_FOUND).build();


	}

}
