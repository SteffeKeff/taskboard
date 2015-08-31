package se.eldebabe.taskboard.data;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.services.WorkItemService;

public class Main {

	public static void main(String args[]){
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()){
			context.scan("se.eldebabe.taskboard.data.configs");
			context.refresh();
			
			WorkItemService workItemService = context.getBean(WorkItemService.class);
			
			workItemService.saveWorkItem(new WorkItem("Hej", "Hej"));
		}
	}
	
}
