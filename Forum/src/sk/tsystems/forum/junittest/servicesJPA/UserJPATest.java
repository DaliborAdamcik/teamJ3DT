package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.UserJPA;

public class UserJPATest {
	private UserJPA userservice;
	private String userName;
	private String password;
	private String realName;
	
	@Before
	public void setUp() throws Exception {
		userservice = new UserJPA(); // start tested service
		userName = TestHelper.randomString(20);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
	}

	@After
	public void tearDown() throws Exception {
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
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersUserRole() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersTopic() {
		fail("Not yet implemented");
	}

}
