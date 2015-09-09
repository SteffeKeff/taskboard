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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.services.IssueService;

@Path("issues")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class IssueWebService{

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static IssueService issueService;
	private static final ObjectMapper mapper = new ObjectMapper();

	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		issueService = context.getBean(IssueService.class);
	}

	@Context
	public UriInfo uriInfo;

	@POST
	public final Response saveIssue(final String json) throws JsonParseException, JsonMappingException, IOException
	{
		Issue issue = mapper.readValue(json, Issue.class);
		
		issue = issueService.saveIssue(issue);
		if(null != issue){			
			return Response.ok(mapper.writeValueAsString(issue)).header("Location", uriInfo.getPath() + "/" + issue.getId().toString()).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("{issueId}")
	public final Response getIssue(@PathParam("issueId") final Long id) throws JsonGenerationException, JsonMappingException, IOException{

		Issue issue = issueService.findIssueById(id);
		if(null != issue){
			return Response.ok(mapper.writeValueAsString(issue)).build();
		}else{
			return Response.noContent().build();
		}
	}

	@DELETE
	@Path("{issueId}")
	public final Response deleteIssue(@PathParam("issueId") final Long id) throws JsonGenerationException, JsonMappingException, IOException
	{
		Issue issue = issueService.findIssueById(id);
		issue = issueService.deleteIssue(id);
		if(null != issue) {
			return Response.ok(mapper.writeValueAsString(issue)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{issueId}")
	public final Response updateIssue(@PathParam("issueId") final Long id, final String json) throws JsonParseException, JsonMappingException, IOException
	{
		Issue issue = mapper.readValue(json, Issue.class);
		issue.setId(id);

		issue = issueService.updateIssue(issue);
		if(null != issue){			
			return Response.ok(mapper.writeValueAsString(issue)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
