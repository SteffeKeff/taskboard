package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.models.WorkItem;

public class UserServiceTest{
	
	private static AnnotationConfigApplicationContext context;
	private User user;
	private static UserService userService;

	@BeforeClass
	public static void setup(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		userService = context.getBean(UserService.class);
	}
	
	@Test
	public void assertThatUserIsSavable(){
		user = new User("1001", "user1", "Olle", "Nilsson");
		assertThat("Added User should be returned", user, is(userService.saveUser(user)));
	}
	
	@Test
	public void assertThatUserCanBeUpdated(){
		user = new User("1002", "user2", "Olle", "Nilsson");
		userService.saveUser(user);
		Long userId = user.getId();
		user = new User("1002", "user2", "Peter", "Svensson");
		user.setId(userId);
		userService.saveUser(user);
		assertThat("User should now be changed", user.getFirstName(), is(userService.findByUserName("user2").getFirstName()));
	}
	
	@Test
	public void assertThatUserCanBeDeleted(){
		user = new User("1003", "user3", "Steff", "keff");
		userService.saveUser(user);
		userService.deleteUser(user.getId());
		assertThat("userService deleted User", null, is(userService.findByUserName("user3")));
	}
	
	/*//// These test have relations ////*/
	
	@Test
	public void assertThatAnUserCanBeAssignedAnWorkItemAndFetchAllWorkItems(){
		WorkItem workItem1 = new WorkItem("nytt work item1", "ett litet uppdrag här!");
		WorkItem workItem2 = new WorkItem("nytt work item2", "ett litet uppdrag här!!");
		WorkItem workItem3 = new WorkItem("nytt work item3", "ett litet uppdrag här!!!");
		user = new User("1010", "user1337", "steffo", "keffo");
		user.addWorkItem(workItem1);
		user.addWorkItem(workItem2);
		user.addWorkItem(workItem3);
		userService.saveUser(user);
		assertThat("the user should now have one workItem", user.getWorkItems().size(), is(userService.findUser("1010").getWorkItems().size()));
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}
}
