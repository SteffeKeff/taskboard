package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		assertThat("User should now be changed", user.getFirstName(),
				is(userService.findByUserName("user2").getFirstName()));
	}

	@Test
	public void assertThatUserCanBeDeleted(){
		user = new User("1003", "user3", "Steff", "keff");
		userService.saveUser(user);
		userService.deleteUser(user.getId());
		assertThat("userService deleted User", null, is(userService.findByUserName("user3")));
	}

	@Test
	public void assertThatUserCanBeFoundByUserId(){
		user = new User("1004", "user4", "Steffe", "Kung");
		userService.saveUser(user);
		assertThat("User is found by it's userID", "1004", is(userService.findUser("1004").getUserId()));
	}

	@Test
	public void assertThatUserIsFoundByFirstname(){
		user = new User("1005", "user5", "Firstname1", "Lastname1");
		userService.saveUser(user);
		assertThat("User is found by it's firstname", "1005",
				is(userService.findByFirstname("Firstname1").get(0).getUserId()));
	}

	@Test
	public void assertThatUserIsFoundByLastname(){
		user = new User("1006", "user6", "Firstname2", "Lastname2");
		userService.saveUser(user);
		assertThat("User is found by it's lastname", "1006",
				is(userService.findByLastname("Lastname2").get(0).getUserId()));
	}

	@Test
	public void assertThatUserIsFoundByUsername(){
		user = new User("1007", "user7", "Firstname3", "Lastname3");
		userService.saveUser(user);
		assertThat("User is found by it's username", "1007", is(userService.findByUserName("user7").getUserId()));
	}

	@AfterClass
	public static void tearDown(){
		userService.clearUsers();
		context.close();
	}
}
