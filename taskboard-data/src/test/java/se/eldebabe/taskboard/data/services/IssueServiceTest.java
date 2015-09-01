package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Issue;

public class IssueServiceTest{

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
		issueService.findIssueById(issue.getId());
		assertThat("issueService should find issue", issue, is(issueService.findIssueById(issue.getId())));
	}

	@Test
	public void assertThatIssueCanBeDeleted(){
		issueService.saveIssue(issue);
		issueService.deleteIssue(issue);
		assertThat("issueService must not find issue", null, is(issueService.findIssueById(issue.getId())));
	}

	@AfterClass
	public static void tearDown(){
		context.close();
	}

}
