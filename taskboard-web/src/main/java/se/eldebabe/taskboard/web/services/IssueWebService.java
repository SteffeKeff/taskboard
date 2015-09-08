package se.eldebabe.taskboard.web.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.services.IssueService;

@Path("issues")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class IssueWebService{

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static IssueService issueService = new IssueService();
	
	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		issueService = context.getBean(IssueService.class);
	}

	@Context
	public UriInfo uriInfo;

	@POST
	public final Response saveIssue(final String description)
	{
		
		JsonObject jo = new Gson().fromJson(description, JsonObject.class);
		String desc = jo.get("description").getAsString();
		Issue issue = new Issue(desc);
		
		issue = issueService.saveIssue(issue);
		if(null != issue){			
			return Response.ok(JsonWriter.toJson(issue)).header("Location", uriInfo.getPath() + "/" + issue.getId().toString()).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("{issueId}")
	public final Response getIssue(@PathParam("issueId") final Long id){

		Issue issue = issueService.findIssueById(id);
		
		if(null != issue){
			return Response.ok(JsonWriter.toJson(issue)).build();
		}else{
			return Response.noContent().build();
		}
	}
	
	@DELETE
	@Path("{issueId}")
	public final Response deleteIssue(@PathParam("issueId") final Long id)
	{
		Issue issue = issueService.deleteIssue(id);
		if(null != issue) {
			return Response.ok(JsonWriter.toJson(issue)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Path("{issueId}")
	public final Response updateIssue(@PathParam("issueId") final Long id, final String description)
	{
		JsonObject jo = new Gson().fromJson(description, JsonObject.class);
		String desc = jo.get("description").getAsString();
		Issue issue = new Issue(desc);
		issue.setId(id);
		
		issue = issueService.updateIssue(issue);
		if(null != issue){			
			return Response.ok(JsonWriter.toJson(issue)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
