package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.WorkItem;

public final class WorkItemServiceTest {

	WorkItemService workItemService;
	WorkItem workItem;
	
	@Before
	public void setup(){
		workItem = new WorkItem("Skapa hemsida", "Lite html, lite css, gärna mycket javascript!");
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()){
			context.scan("se.eldebabe.taskboard.data.configs");
			context.refresh();
			
			workItemService = context.getBean(WorkItemService.class);
		}
	}
	
	@Test
	public void assertThatWorkItemIsSavable(){

		assertThat("Added WorkItem should be returned", workItem, is(workItemService.saveWorkItem(workItem)));
		
	}

}
