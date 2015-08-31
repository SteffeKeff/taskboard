package se.eldebabe.taskboard.data.services;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.eldebabe.taskboard.data.models.WorkItem;

public class WorkItemServiceTest {

	private static WorkItemService workItemService;
	private static WorkItem workItem;
	
	@Before
	public static void setup(){
		workItem = new WorkItem("Skapa hemsida", "Lite html, lite css, gärna mycket javascript!");
		workItemService = new WorkItemService();
	}
	
	@Test
	public void assertThatWorkItemIsSavable(){

		assertThat(workItem, equals(workItem.))
		
		
	}

}
