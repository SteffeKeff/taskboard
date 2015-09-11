package se.eldebabe.taskboard.web.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.services.WorkItemService;

@Path("workitems")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class WorkItemWebService {

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private static WorkItemService workItemService = new WorkItemService();
	private static final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

	static {
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		workItemService = context.getBean(WorkItemService.class);
	}

	@Context
	public UriInfo uriInfo;

	@POST
	public final Response saveWorkItem(final String json) throws JsonParseException, JsonMappingException, IOException {
		WorkItem workItem = mapper.readValue(json, WorkItem.class);

		workItem = workItemService.saveWorkItem(workItem);
		if (null != workItem) {
			return Response.ok(mapper.writeValueAsString(workItem))
					.header("Location", uriInfo.getPath() + "/" + workItem.getId().toString()).build();
		} else {
			Response.notAcceptable(new ArrayList<Variant>());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("issue")
	public final Response getWorkItemsWithIssues(@QueryParam("issue") final boolean hasIssue)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException {

		if (hasIssue) {
			ArrayList<WorkItem> workItems = (ArrayList<WorkItem>) workItemService.findWorkItemsWithIssue();
			if (!workItems.isEmpty()) {
				return Response.ok(mapper.writeValueAsString(workItems)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("description")
	public final Response searchForWorkItemsWithDesc(@QueryParam("description") final String description)
			throws com.fasterxml.jackson.core.JsonGenerationException,
			com.fasterxml.jackson.databind.JsonMappingException, IOException {

		ArrayList<WorkItem> workItems = (ArrayList<WorkItem>) workItemService
				.findWorkItemWithDescriptionContaining(description);

		if (!workItems.isEmpty()) {
			return Response.ok(mapper.writeValueAsString(workItems)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{workItemId}/issue")
	public final Response updateIssue(@PathParam("workItemId") final Long id, final String json)
			throws JsonParseException, JsonMappingException, IOException {
		Issue issue = mapper.readValue(json, Issue.class);

		WorkItem workItem = workItemService.findWorkItem(id);
		workItem.setIssue(issue);

		workItem = workItemService.saveWorkItem(workItem);
		if (null != issue) {
			return Response.ok(mapper.writeValueAsString(workItem)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{workItemId}/status")
	public final Response changeStatus(@PathParam("workItemId") final Long id, final String json)
			throws JsonParseException, JsonMappingException, IOException {
		se.eldebabe.taskboard.data.models.Status status;

		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);

		status = se.eldebabe.taskboard.data.models.Status.valueOf(jobj.get("status").getAsString());

		if (null != status) {
			WorkItem workItem = workItemService.findWorkItem(id);
			if (null != workItem) {
				workItem.setCompleted(status);

				workItem = workItemService.saveWorkItem(workItem);
				return Response.ok(mapper.writeValueAsString(workItem)).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Status.EXPECTATION_FAILED).build();
	}

	@DELETE
	@Path("{workItemId}")
	public final Response deleteWorkItem(@PathParam("workItemId") final Long id)
			throws JsonParseException, JsonMappingException, IOException {
		WorkItem workItem = workItemService.findWorkItem(id);

		if (null != workItem) {
			workItemService.deleteWorkItem(id);
			return Response.ok(mapper.writeValueAsString(workItem)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/status/{workItemStatus}")
	public final Response getWorkitemWithStatus(@PathParam("workItemStatus") final String workItemStatus)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<WorkItem> workItems = null;
		se.eldebabe.taskboard.data.models.Status status = null;

		try {
			status = se.eldebabe.taskboard.data.models.Status.valueOf(workItemStatus.toUpperCase());
		} catch (RuntimeException IllegalArgumentException) {
			return Response.status(406).build();
		}

		workItems = workItemService.findWorkItemsWithStatus(status);
		if (null != workItems) {
			return Response.ok(mapper.writeValueAsString(workItems)).build();
		} else {
			return Response.noContent().build();
		}
	}

}
