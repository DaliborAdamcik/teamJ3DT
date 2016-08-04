package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;


import java.util.Date;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.UserJPA;

public class UserJPATest {
	private UserJPA userservice;
	private String userName;
	private String password;
	private String realName;
	private Date birthDate;
	
	@Before
	public void setUp() throws Exception {
		userservice = new UserJPA(); // start tested service
		userName = TestHelper.randomString(20);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
		birthDate = new Date();
	}

	@After
	public void tearDown() throws Exception {
		// tu testovat remove user
	}

	/**
	 * Test for good initialization of object
	 */
	@Test
	public void testUserJPA() {
		assertNotNull("Object is badly initialized", userservice);
		UserJPA secondinstance = new UserJPA();
		assertNotNull("Object is badly initialized (try create new object)", secondinstance);
	}

	@Test
	public void testAddUser() {
		// create a new user
		Date regDate = new Date();
		User user = new User(userName, password, birthDate, realName);
		// add user
		userservice.addUser(user);
		//try to select user from DB
		User userTest = userservice.getUser(user.getId());
		assertNotNull("Persistence of user failed", userTest);
		assertEquals("Bad name", userTest.getRealName(), realName);
		assertEquals("Bad real name", userTest.getUserName(), userName);
		assertEquals("Bad birth date", userTest.getBirthDate().getTime() / 1000, birthDate.getTime() / 1000);
		assertEquals("Bad password", userTest.getPassword(), password);
		assertEquals("User cant be blocked", userTest.getBlocked(), null);
		assertEquals("BAD role", userTest.getRole(), UserRole.GUEST);
		assertEquals("Bad reg date", userTest.getRegistrationDate().getTime() / 1000, regDate.getTime() / 1000);
		assertTrue("Bad ID in DB", userTest.getId()>0);
	}

	@Test
	public void testRemoveUser() { // toto nerobit, vymazat potom
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() { // toto dat viac krat ako napriklad testUpdateUserPassword, testUpdateUserName atï
		User user = new User(userName, password, birthDate, realName);
		// add user
		userservice.addUser(user);
		int userid = user.getId();
		//change password
		String newPassword=TestHelper.randomString(20);
		user.setPassword(newPassword);
		userservice.updateUser(user);
		assertEquals("BAD USER ID!!", user.getId(), userid);
		
		//znovu nacitat password
		User updatedUser = userservice.getUser(user.getId());
		assertEquals("BAD USER ID!!", updatedUser.getId(), userid);

		assertEquals("username not updated sucessfully", updatedUser.getPassword(),newPassword);
	}

	@Test
	public void testGetUserString() {
		
		
		Date regDate = new Date();
		User user = new User(userName, password, birthDate, realName);
		// add user
		userservice.addUser(user);
		//try to select user from DB
		
		User userTest = userservice.getUser(userName);
		assertNotNull("Persistence of user failed", userTest);
		assertEquals("Bad name", userTest.getRealName(), realName);
		assertEquals("Bad real name", userTest.getUserName(), userName);
		assertEquals("Bad birth date", userTest.getBirthDate().getTime() / 1000, birthDate.getTime() / 1000);
		assertEquals("Bad password", userTest.getPassword(), password);
		assertEquals("User can't be blocked", userTest.getBlocked(), null);
		assertEquals("BAD role", userTest.getRole(), UserRole.GUEST);
		assertEquals("Bad registration date", userTest.getRegistrationDate().getTime() / 1000, regDate.getTime() / 1000);
		assertTrue("Bad ID in DB", userTest.getId()>0);
		
	}

	@Test
	public void testGetUserInt() {
		Date regDate = new Date();
		User user = new User(userName, password, birthDate, realName);
		//add user
		userservice.addUser(user);
		int userid= user.getId();
				
		assertNotNull("Persistence of user failed", userservice.getUser(userName));
		assertEquals("Bad name", userservice.getUser(userName).getId(),userid);
		
	}

	@Test
	public void testGetUsersUserRole() {
		List<User> userList = userservice.getUsers(UserRole.GUEST);
		for (User user:userList){
			assertEquals("User role is not guest", user.getRole(), UserRole.GUEST);
			
		}
	}

	@Test
	public void testGetUsersTopic() {
		fail("Not yet implemented");
	}

}
