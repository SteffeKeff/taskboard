package se.eldebabe.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.services.IssueService;

@Path("issues")
public final class IssueWebService{

	private IssueService issueService = new IssueService();

	@Context
	public UriInfo uriInfo;

	@GET
	@Path("{issueId}")
	public final Response getIssue(@PathParam("issueId") final Long id){
		Issue issue = issueService.findIssueById(id);
		if(issue == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(issue).build();
	}

}
