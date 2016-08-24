package sk.tsystems.forum.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.entity.exceptions.field.user.BadDateException;
import sk.tsystems.forum.entity.exceptions.field.user.NickNameException;
import sk.tsystems.forum.entity.exceptions.field.user.PasswordCheckException;
import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;
import sk.tsystems.forum.helper.UserHelper;

/**
 * Entity for User containing {@link String} user's nickname, {@link String} user's password, {@link Date} user's date of birth, {@link String} user's realname and user role (possible roles listed in enum {@UserRole})
 * 
 * @author J3DT
 */
@Entity
@Table(name = "JPA_USER")
public class User extends BlockableEntity implements Comparable<User> {

	/** {@link String} field <b>user's nickname</b> */
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String userName;

	/** {@link String} field <b>user's password</b> */
	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	/**
	 * Birth Date (java.util.date) {@link Date} of user
	 */
	@Column(name = "BIRTHDATE", nullable = false)
	private Date birthDate;

	/** {@link String} field <b>user's real name</b> */
	@Column(name = "NAME")
	private String realName;

	/**
	 * User Role. Possible roles listed in enum {@UserRole}
	 */
	@Column(name = "ROLE")
	private UserRole role;

	/**
	 * Many-to-many connection between topics and users
	 */
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private List<Topic> topics;

	// /**
	// * One-To-Many connection between user and comments
	// */
	// @OneToMany
	// List<Comment> comments; // TODO implementovat list ak nam ho bude treba

	/**
	 * Create new user
	 *
	 * @param userName
	 *            {@link String} user's nickname
	 * @param password
	 *            {@link String} user's password
	 * @param birthDate
	 *            {@link Date} user's date of birth
	 * @param realName
	 *            {@link String} user's real name
	 * @throws {@link
	 *             NickNameException}
	 * @throws {@link
	 *             PasswordCheckException}
	 * @throws {@link
	 *             UserEntityFieldException}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public User(String userName, String password, Date birthDate, String realName)
			throws NickNameException, PasswordCheckException, UserEntityFieldException, FieldValueException {
		this();
		setUserName(userName);
		setPassword(password);
		setBirthDate(birthDate);
		setRealName(realName);
	}

	/**
	 * Create new user
	 * 
	 * @param userName
	 *            {@link String} user's nickname
	 * @param password
	 *            {@link String} user's password
	 * @param birthDate
	 *            {@link Date} user's date of birth
	 * @param realName
	 *            {@link String} user's real name
	 * @throws {@link
	 *             NickNameException}
	 * @throws {@link
	 *             PasswordCheckException}
	 * @throws {@link
	 *             BadDateException}
	 * @throws {@link
	 *             UserEntityFieldException}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public User(String userName, String password, String birthDate, String realName) throws NickNameException,
			PasswordCheckException, BadDateException, UserEntityFieldException, FieldValueException {
		this(userName, password, UserHelper.stringToDate(birthDate), realName);
	}

	/**
	 * Constructor only for JPA, must be private
	 */
	private User() {
		super();
		topics = new ArrayList<Topic>();
		this.role = UserRole.GUEST;
	}

	/**
	 * Iterator for topics
	 * 
	 * @return iterator of topics
	 */
	public Iterator<Topic> getTopicsIterator() {
		return topics.iterator();
	}

	/**
	 * Add topic to list of topics
	 * 
	 * @param Topic
	 *            {@link Topic}
	 */
	public void addTopic(Topic topic) {
		topics.add(topic);
	}

	/**
	 * Add topics to list of topics
	 * 
	 * @param list
	 *            of topics to be added
	 */
	public void addTopicList(List<Topic> list) {
		topics.addAll(list);

	}

	/**
	 * Remove topic from the list of topics
	 * 
	 * @param Topic
	 *            {@link Topic}
	 */
	public void removeTopic(Topic topic) {
		topics.remove(topic);
	}

	/**
	 * Getter for userName
	 * 
	 * @return {@link String} user's nickname
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for userName
	 * 
	 * @param userName
	 *            {@link String} user's nickname
	 * @throws {@link
	 *             NickNameException}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setUserName(String userName) throws NickNameException, FieldValueException {
		testNotEmpty(userName, "user name", true);
		UserHelper.nickNameValidator(userName);
		this.userName = userName;
	}

	/**
	 * Setter for password
	 * 
	 * @param password
	 *            {@link String} user's password
	 * @throws {@link
	 *             PasswordCheckException}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setPassword(String password) throws PasswordCheckException, FieldValueException {
		testNotEmpty(password, "password", true);
		UserHelper.passwordOverallControll(password);
		this.password = password;
	}

	/**
	 * Getter for birthDate
	 * 
	 * @return {@link Date} user's date of birth
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter for birthDate
	 * 
	 * @param birthDate
	 *            {@link Date} user's date of birth
	 * @throws {@link
	 *             FieldValueException}
	 * @throws {@link
	 *             BadDateException}
	 */
	public void setBirthDate(Date birthDate) throws FieldValueException, BadDateException {
		testNotEmpty(birthDate, "birth date", false);
		if (birthDate.after(new Date()))
			throw new BadDateException("Birthday cant be after actual date.");
		this.birthDate = birthDate;
	}

	/**
	 * Setter for birthDate This setter parses String to Date and saves (if
	 * conversion is success)
	 * 
	 * @param birthDate
	 *            {@link Date} user's date of birth
	 * @throws {@link
	 *             BadDateException}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setBirthDate(String birthDate) throws BadDateException, FieldValueException {
		testNotEmpty(birthDate, "birth date", false);
		setBirthDate(UserHelper.stringToDate(birthDate));
	}

	/**
	 * Getter for role
	 * 
	 * @return user role possible role listed in enum {@UserRole}
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * Setter for role
	 * 
	 * @param role
	 *            possible role listed in enum {@UserRole}
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Getter for real name
	 * 
	 * @return {@link String} user's real name
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Setter for realName
	 * 
	 * @param realName
	 *            {@link String} user's real name
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setRealName(String realName) throws FieldValueException {
		testNotEmpty(realName, "real name", true);
		// TODO !!!!!! CHECK REAL NAME FOR SPECIAL CHARS
		this.realName = realName;
	}

	/**
	 * Check passwords
	 * 
	 * @param password
	 *            {@link String} password which is checked
	 * @return boolean true if passwords equal, else false
	 */
	public boolean authentificate(String password) {
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}

	/**
	 * String representation of user
	 * 
	 * @return {@link String} representation of user
	 */
	@Override
	public String toString() {
		return String.format("User [userName=%s, password=%s, birthDate=%s, realName=%s, role=%s, topics=%s]", userName,
				password, birthDate, realName, role, topics);
	}

	/**
	 * Overrides java.lang.Object.equals
	 *
	 * @param object
	 *            {@link Object}
	 * @return true if <b>this</b> has the same id as {@link Object} in the
	 *         parameter
	 */
	public boolean equals(Object object) {
		if (object instanceof User) {
			if (this.getId() == ((User) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable<User>.compareTo
	 * 
	 * @param {@link
	 * 			User}
	 * @return returned value(integer) is > 0 when the user's name is higher in
	 *         alphabetical order than the name of the user in parameter, = 0 if
	 *         equal, < 0 if lower
	 */
	@Override
	public int compareTo(User user) {
		return this.userName.compareTo(user.getUserName());
	}

	/**
	 * Block or unblock user
	 * 
	 * @param {@link
	 * 			Blocked}
	 * @throws {@link
	 *             FieldValueException}
	 */
	@Override
	public final void setBlocked(Blocked blocked) throws FieldValueException {
		if (equals(blocked.getBlockedBy()))
			throw new RuntimeException("YOU CAND DO THIS ANYTIME!!!!!!!!!!!!!!!!!!! RYS(Z)AAA");
		super.setBlocked(blocked);
	}

}
