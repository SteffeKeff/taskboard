package se.eldebabe.taskboard.web.services;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.gson.Gson;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.services.TeamService;

@Path("teams")
@Produces({javax.ws.rs.core.MediaType.APPLICATION_JSON})
public class TeamWebService {
	
	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private TeamService teamService;
	Gson gson = new Gson();
	
	
	@GET
	public Response getAllTeams()
	{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		teamService = context.getBean(TeamService.class);
		
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
	public final Response deleteTeam(@PathParam("name") final String name) {
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		teamService = context.getBean(TeamService.class);
		System.out.println(name);
		
		teamService.deleteByName(name);
		return Response.ok(name).build();
	}

}
