package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.NickNameException;

public class UserEntityTest {

	private String userName;
	private String password;
	private String realName;
	private Date birthDate;
	private Date actualDate;

	@Before
	public void setUp() throws Exception {
		actualDate = new Date();
		userName = TestHelper.randomString(20);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
		birthDate = new Date();
	}

	@Test
	public void userNameTest() throws NickNameException {

		
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Username set without constructor", user.getUserName());
		user.setUserName(userName);
		assertEquals("Username does not match", user.getUserName(), userName);
	}
	
	@Test
	public void RealNameTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Realname set without constructor", user.getRealName());
		user.setRealName(realName);
		assertEquals("realname does not match", user.getRealName(), realName);
	}

	@Test
	public void passwordTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Password set without constructor", user.getPassword());
		user.setPassword(password);
		assertEquals("Password does not match", user.getPassword(), password);

	}

	@Test
	public void birthDateTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("BithDate set without constructor", user.getBirthDate());
		user.setBirthDate(birthDate);
		assertEquals("Birthdate does not match", user.getBirthDate(), birthDate);

	}

	@Test
	public void registrationDateTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNotNull("regDate is null", user.getRegistrationDate());
		assertEquals("regdate not equal", user.getRegistrationDate().getTime() / 100, actualDate.getTime() / 100);
	}

	@Test
	public void getInitRoleTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNotNull("role(init) is null", user.getRole());
		assertEquals("role not set in constructor", user.getRole(), UserRole.GUEST);
	}

	@Test
	public void roleTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		UserRole role = UserRole.REGULARUSER;
		user.setRole(role);
		assertEquals("role not equal", user.getRole(), role);
		role = UserRole.ADMIN;
		user.setRole(role);
		assertEquals("role not equal", user.getRole(), role);
	} 

}
