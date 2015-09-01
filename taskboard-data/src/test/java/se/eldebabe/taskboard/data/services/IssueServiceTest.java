package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Issue;

public final class IssueServiceTest{

	private static AnnotationConfigApplicationContext context;
	private IssueService issueService;
	private Issue issue;

	@BeforeClass
	public static void setupConfigs(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
	}

	@Before
	public void setup(){
		issue = new Issue("Förlängd leveranstid till 6/9-15");
		issueService = context.getBean(IssueService.class);
	}

	@Test
	public void assertThatIssueIsSavable(){
		assertThat("Added Issue should be returned", issue, is(issueService.saveIssue(issue)));
	}

	@Test
	public void assertThatIssueIsGettable(){
		issueService.saveIssue(issue);
		assertThat("issueService should find issue", 1L, is(issueService.findIssueById(1L).getId()));
	}
	
	@Test 
	public void assertThatIssueIsNotGettable(){
		assertThat("", null, is(issueService.findIssueById(5L)));
	}
	
	@Test
	public void assertThatIssueCanBeUpdated(){
		issueService.saveIssue(issue);
		issue.setDescription("Förlängd leveranstid till 18/9-15");
		issueService.saveIssue(issue);
		assertThat("", issue.getDescription(), is(issueService.findIssueById(1L).getDescription()));
	}

	@Test
	public void assertThatIssueCanBeDeleted(){
		issueService.saveIssue(issue);
		issueService.deleteIssue(issue);
		assertThat("issueService must not find issue", null, is(issueService.findIssueById(issue.getId())));
	}
	
	//uppdatera. se till att det smäller fint då idt inte finns

	@AfterClass
	public static void tearDown(){
		context.close();
	}

}
