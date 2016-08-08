package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.TopicJPA;
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
		//TODO neviem na co to je
		/* tato cast kodu sluzi na zavolanei po vsetkych testoch
		 * tu by sa mali mazat temporary udaje a podobne, ktore by len zahlcovali databazy"
		*/
		
		
	}
//	@Test
//	public void testUserRemove() {
//		User user = new User(userName, password, birthDate, realName);
//		userservice.addUser(user);
//		assertEquals("BAD USER ID!!", user.getId(),user.getId());
//		userservice.removeUser(user);
//		
//		assertNull("user was not removed", user);
//	}
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

		assertTrue("Bad ID in DB", userTest.getId()>0);
		assertEquals("Bad tstUSR1.ID vs tstUSR2.ID", userTest.getId(), user.getId());

		assertEquals("Bad name", userTest.getRealName(), realName);
		assertEquals("Bad real name", userTest.getUserName(), userName);
		assertEquals("Bad birth date", userTest.getBirthDate().getTime() / 1000, birthDate.getTime() / 1000);
		assertEquals("Bad password", userTest.getPassword(), password);
		assertNull("User cant be blocked", userTest.getBlocked());
		assertEquals("BAD role", userTest.getRole(), UserRole.GUEST);
		assertEquals("Bad reg date", userTest.getRegistrationDate().getTime() / 1000, regDate.getTime() / 1000);
	}

	/**
	 * Test service to duplicate add of user into database.
	 * @author Dalibor
	 */
	@Test
	public void testAddUserDuplicity() {
		userName = TestHelper.randomString(30);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
		
		// create a new user
		User user = new User(userName, password, birthDate, realName);

		// add user # 1st. try - must be success (condition is not already exists)
		assertTrue("Cant add test user #1", userservice.addUser(user));
		// add user # 2nd. try
		assertFalse("Cant add test user #2", userservice.addUser(user));
		
		List<User> controlList = userservice.getAllUsers();
		
		int inList = 0;
		for (User user2 : controlList) {
			if(user2.getUserName().compareTo(user.getUserName())==0)
				inList++;
		}
		assertEquals("User not fond / more times saved in list", 1, inList);
		
		
		assertTrue("Remove temporary User", userservice.removeUser(user));
		assertNull("Failed to remove user / Duplicate entries in DB", userservice.getUser(user.getId()));
		
		
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
	//	Date regDate = new Date();
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
		
		TopicJPA topicservice = new TopicJPA();
		//User1 - With topic "topic1"
		User user1 = new User(userName, password, birthDate, realName);
		String topic1Name =TestHelper.randomString(20);
		Topic topic1 = new Topic(topic1Name,true);
		topicservice.addTopic(topic1);
		user1.addTopic(topic1);
		userservice.addUser(user1);
		
		//User2 - with topics topic2,topic3
		User user2 = new User(TestHelper.randomString(20), TestHelper.randomString(20), new Date(), TestHelper.randomString(20));
		String topic2Name = TestHelper.randomString(20);
		Topic topic2 = new Topic(topic2Name,true);
		topicservice.addTopic(topic2);
		user2.addTopic(topic2);
		String topic3Name = TestHelper.randomString(20);
		Topic topic3 = new Topic(topic3Name,true);
		topicservice.addTopic(topic3);
		user2.addTopic(topic3);
		userservice.addUser(user2);
		
		//user3 with topics topic1,topic2,topic3
		User user3 = new User(TestHelper.randomString(20), TestHelper.randomString(20), new Date(), TestHelper.randomString(20));
		user3.addTopic(topic1);
		user3.addTopic(topic2);
		user3.addTopic(topic3);
		userservice.addUser(user3);
		
		//creation of expected lists
		
		List<User> listOfUsersWithTopic1 =new ArrayList<User>();
		listOfUsersWithTopic1.add(user1);
		listOfUsersWithTopic1.add(user3);
		
		//tests
		for(int listPosition=0;listPosition<listOfUsersWithTopic1.size();listPosition++){
		assertEquals("Lists are not equal",listOfUsersWithTopic1.get(listPosition).getId(),userservice.getUsers(topic1).get(listPosition).getId() );
		}
		
		
	}

}
