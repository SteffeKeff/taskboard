package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.User;

public class UserServiceTest{
	
	private static AnnotationConfigApplicationContext context;
	private User user;
	private UserService userService;

	@BeforeClass
	public static void setupConfigs(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
	}
	
	@Before
	public void setup(){
		user = new User("1001", "user1", "Olle", "Nilsson");
		userService = context.getBean(UserService.class);
	}
	
	@Test
	public void assertThatUserIsSavable(){
		assertThat("Added User should be returned", user, is(userService.saveUser(user)));
	}
	
	@Test
	public void assertThatUserCanBeUpdated(){
		userService.saveUser(user);
		Long userId = user.getId();
		user = new User("1002", "user2", "Peter", "Svensson");
		userService.saveUser(user);
		assertThat("User should now be changed", userId, is(userService.findByUserName("user2").getId()));
	}
	
	@Test
	public void assertThatUserCanBeDeleted(){
		user = new User("1001", "user1", "Olle", "Nilsson");
		userService.saveUser(user);
		userService.deleteUser(user.getId());
		assertThat("userService deleted User", null, is(userService.findByUserName("user1")));
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}
}
