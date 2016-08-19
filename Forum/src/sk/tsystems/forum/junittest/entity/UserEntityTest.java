package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.exceptions.CommonEntityException;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.BadDateException;
import sk.tsystems.forum.helper.exceptions.FieldException;
import sk.tsystems.forum.helper.exceptions.NickNameException;
import sk.tsystems.forum.helper.exceptions.PasswordCheckException;
import sk.tsystems.forum.helper.exceptions.UserEntityException;

public class UserEntityTest {

	private String userName;
	private String password;
	private String realName;
	private Date birthDate;
	private Date actualDate;

	@Before
	public void setUp() throws Exception {
		actualDate = new Date();
		userName = TestHelper.randomString(20, 0).toLowerCase();
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20) + "Aa.1";
		birthDate = TestHelper.randomDate();
	}

	@Test
	public void userNameTest() throws NickNameException { // OK, dalik 11.8.2016
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Username set without constructor", user.getUserName());
		assertTrue("Cant set user name to null", userDoTestException(user, null));
		assertTrue("Cant set user name to empty string", userDoTestException(user, ""));
		assertTrue("User name length must be 4+", userDoTestException(user, "usr"));
		assertTrue("User name cant start with number", userDoTestException(user, "0usr"));
		assertTrue("User name cant contain uppercases", userDoTestException(user, "usrAK"));
		assertTrue("User name cant contain invalid characters", userDoTestException(user, "@/**+-sd"));
		assertFalse("Cant set valid username", userDoTestException(user, userName));
		assertEquals("Username does not match", user.getUserName(), userName);
	}

	public boolean userDoTestException(User user, String name) { // OK, dalik,
																	// 11.8.2016
		try {
			user.setUserName(name);
			return false;
		} catch (NickNameException | FieldException e) {
			System.out.println("** User name check exception: " + e.getMessage() + " (is OK)");
			return true;
		}
	}

	@Test
	public void RealNameTest() throws UserEntityException, FieldException {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("Realname set without constructor", user.getRealName());
		user.setRealName(realName);
		assertEquals("realname does not match", user.getRealName(), realName);
	}

	@Test
	public void passwordTest() throws PasswordCheckException { // OK, dalik
																// 11.8.2016
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);
		assertTrue("Cant set empty password", passwordDoTestException(user, ""));
		assertTrue("Cant set short password (length<8)", passwordDoTestException(user, TestHelper.randomString(7)));
		assertTrue("Cant set simple password", passwordDoTestException(user, "abcdefgh"));
		assertTrue("Cant set simple with numeric", passwordDoTestException(user, "abcdefgh1"));
		assertTrue("Cant set simple with special", passwordDoTestException(user, "abcdefgh/"));
		assertFalse("Cant set valid password", passwordDoTestException(user, password + "12@/"));
		assertTrue("Password does not match", user.authentificate(password + "12@/"));
	}

	public boolean passwordDoTestException(User user, String password) { // OK,
																			// dalik,
																			// 11.8.2016
		try {
			user.setPassword(password);
			return false;
		} catch (PasswordCheckException|FieldException e) {
			System.out.println("** Password check exception: " + e.getMessage() + " (is OK)");
			return true;
		}
	}

	@Test
	public void birthDateTest() throws BadDateException, FieldException { // set
																			// birth
																			// date
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNull("BithDate set without constructor", user.getBirthDate());
		user.setBirthDate(birthDate);
		assertEquals("Birthdate does not match", user.getBirthDate(), birthDate);
	}

	// TODO test string birth date

	@Test
	public void registrationDateTest() {
		User user = TestHelper.nonParaConstructor(User.class);
		assertNotNull("User is badly initialized", user);

		assertNotNull("regDate is null", user.getCreated());
		assertEquals("regdate not equal", user.getCreated().getTime() / 100, actualDate.getTime() / 100);
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

	@Test
	public void equalsTest() throws NickNameException, PasswordCheckException, UserEntityException, FieldException {
		Object o = new User(userName, password, birthDate, realName);
		System.out.println(o + userName);
		User randomUser = new User(userName, password, birthDate, realName);

		assertTrue("method equals doesnt work", randomUser.equals(o));
	}

	@Test
	public void compareToTest() throws NickNameException, PasswordCheckException, UserEntityException, FieldException {
		User randomUser1 = new User(TestHelper.randomString(20, 0).toLowerCase(), password, birthDate, realName);
		User randomUser2 = new User(userName, password, birthDate, realName);
		User randomUser3 = new User(userName, password, birthDate, realName);

		assertEquals("method compareTo doesnt work", randomUser1.compareTo(randomUser2),
				randomUser1.getUserName().compareTo(randomUser2.getUserName()));
		assertEquals("method compareTo doesnt work", randomUser2.compareTo(randomUser3), 0);

	}

}
