package se.eldebabe.taskboard.web.services;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.services.WorkItemService;


@Path("workitems")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class WorkItemWebService {
	
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static WorkItemService workItemService = new WorkItemService();
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static{
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		workItemService = context.getBean(WorkItemService.class);
	}

	@Context
	public UriInfo uriInfo;

	@POST
	public final Response saveWorkItem(final String json) throws JsonParseException, JsonMappingException, IOException
	{
		WorkItem workItem= mapper.readValue(json, WorkItem.class);
		
		workItem = workItemService.saveWorkItem(workItem);
		if(null != workItem){			
			return Response.ok(mapper.writeValueAsString(workItem)).header("Location", uriInfo.getPath() + "/" + workItem.getId().toString()).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("{workItemId}")
	public final Response getIssue(@PathParam("workItemId") final Long id) throws JsonGenerationException, JsonMappingException, IOException{

		WorkItem workItem = workItemService.findWorkItem(id);
		
		if(null != workItem){
			return Response.ok(mapper.writeValueAsString(workItem)).build();
		}else{
			return Response.noContent().build();
		}
	}
	
	@POST
	@Path("{workItemId}/issue")
	public final Response updateIssue(@PathParam("workItemId") final Long id, final String json) throws JsonParseException, JsonMappingException, IOException
	{
		Issue issue = mapper.readValue(json, Issue.class);
		
		WorkItem workItem = workItemService.findWorkItem(id);
		workItem.setIssue(issue);
		
		workItem = workItemService.saveWorkItem(workItem);
		if(null != issue){			
			return Response.ok(mapper.writeValueAsString(workItem)).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}


}
