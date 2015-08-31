package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.WorkItem;

public final class WorkItemServiceTest {

	private static AnnotationConfigApplicationContext context;
	private WorkItemService workItemService;
	private WorkItem workItem;
	
	@BeforeClass
	public static void setupConfigs(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
	}
	
	@Before
	public void setup(){
		workItem = new WorkItem("Skapa hemsida", "Lite html, lite css, gärna mycket javascript!");
		workItemService = context.getBean(WorkItemService.class);
	}
	
	@Test
	public void assertThatWorkItemIsSavable(){
		assertThat("Added WorkItem should be returned", workItem, is(workItemService.saveWorkItem(workItem)));
	}
	
	@Test
	public void assertThatWorkItemStatusCanBeChanged(){
		workItem.setCompleted(true);
		workItemService.saveWorkItem(workItem);
		assertThat("WorkItem status should be true", true, is(workItemService.findWorkItem(workItem.getId()).isCompleted()));
	}
	
	@Test
	public void assertThatWorkItemCanBeDeleted(){
		workItemService.saveWorkItem(workItem);
		workItemService.deleteWorkItem(workItem.getId());
		assertThat("workItemService cant find work item", null, is(workItemService.findWorkItem(workItem.getId())));
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}

}
