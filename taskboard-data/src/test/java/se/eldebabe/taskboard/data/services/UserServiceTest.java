package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.User;

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
		userService.updateUser(userId, user);
		assertThat("User should now be changed", user.getFirstName(), is(userService.findByUserName("user2").getFirstName()));
	}
	
	@Test
	public void assertThatUserCanBeDeleted(){
		user = new User("1003", "user3", "Steff", "keff");
		userService.saveUser(user);
		userService.deleteUser(user.getId());
		assertThat("userService deleted User", null, is(userService.findByUserName("user3")));
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}
}
