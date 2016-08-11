package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.NickNameException;
import sk.tsystems.forum.helper.exceptions.PasswordCheckException;

public class UserEntityTest {

	private String userName;
	private String password;
	private String realName;
	private Date birthDate;
	private Date actualDate;

	@Before
	public void setUp() throws Exception {
		actualDate = new Date();
		userName = TestHelper.randomString(20, 0);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
		birthDate = TestHelper.randomDate();
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
	public void passwordTest() throws PasswordCheckException { // OK, dalik 11.8.2016
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Password with non-parameteric constructor must be null", user.getPassword());
		assertTrue("Cant set null password", passwordDoTestException(user, null));
		assertTrue("Cant set empty password", passwordDoTestException(user, ""));
		assertTrue("Cant set short password (length<8)", passwordDoTestException(user, TestHelper.randomString(7)));
		assertTrue("Cant set simple password", passwordDoTestException(user, "abcdefgh"));
		assertTrue("Cant set simple with numeric", passwordDoTestException(user, "abcdefgh1"));
		assertTrue("Cant set simple with special", passwordDoTestException(user, "abcdefgh/"));
		assertNull("Password must be null", user.getPassword());

		assertFalse("Cant set valid password", passwordDoTestException(user, password+"12@/"));
		assertEquals("Password does not match", password+"12@/", user.getPassword());
	}

	public boolean passwordDoTestException(User user, String password) { // OK, dalik, 11.8.2016
		try {
			user.setPassword(password);
			return false;
		} catch (PasswordCheckException e) {
			System.out.println("** Password check exception: "+e.getMessage()+ " (is OK)");
			return true;
		}
	}
	
	@Test
	public void birthDateTest() { // set birth date
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
