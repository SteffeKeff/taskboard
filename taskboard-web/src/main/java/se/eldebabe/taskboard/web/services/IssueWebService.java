package se.eldebabe.taskboard.web.services;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.services.IssueService;

@Path("issues")
public final class IssueWebService{

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	private IssueService issueService = new IssueService();

	@Context
	public UriInfo uriInfo;

	@POST
	public final Response saveIssue(Issue issue)
	{
		issue = issueService.saveIssue(issue);
		final URI location = uriInfo.getAbsolutePathBuilder().path(issue.getId().toString()).build();
		return Response.created(location).build();
	}
	
	@GET
	@Path("{issueId}")
	public final Response getIssue(@PathParam("issueId") final Long id) throws JAXBException{
		
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh(); 
		issueService = context.getBean(IssueService.class);
		
		Issue issue = new Issue("ett problem");
		JAXBContext jc;
		jc = JAXBContext.newInstance(Issue.class);
		Marshaller marshaller = jc.createMarshaller();


		
//		Issue issue = issueService.findIssueById(id);
//		if(issue == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		return Response.ok(issue).build();
	}
	
	@DELETE
	@Path("{issueId}")
	public final Response deleteIssue(@PathParam("issueId") final Long id)
	{
		Issue issue = issueService.findIssueById(id);
		if(issue == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(issueService.deleteIssue(issue)).build();
	}

}
