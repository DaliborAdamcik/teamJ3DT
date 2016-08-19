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
import sk.tsystems.forum.helper.UserHelper;
import sk.tsystems.forum.helper.exceptions.BadDateException;
import sk.tsystems.forum.helper.exceptions.FieldException;
import sk.tsystems.forum.helper.exceptions.NickNameException;
import sk.tsystems.forum.helper.exceptions.PasswordCheckException;
import sk.tsystems.forum.helper.exceptions.UserEntityException;

@Entity
@Table(name = "JPA_USER")
public class User extends BlockableEntity implements Comparable<User> {

	/**
	 * User Name
	 */
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String userName;

	/**
	 * Password
	 */
	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	/**
	 * Birth Date (java.util.date)
	 */
	@Column(name = "BIRTHDATE", nullable = false)
	private Date birthDate;

	/**
	 * Real name
	 */
	@Column(name = "NAME")
	private String realName;

	/**
	 * User Role. possible roles listed in enum UserRole
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

	public User(String userName, String password, Date birthDate, String realName)
			throws NickNameException, PasswordCheckException, UserEntityException, FieldException {
		this();
		setUserName(userName);
		setPassword(password);
		setBirthDate(birthDate);
		setRealName(realName);
	}

	public User(String userName, String password, String birthDate, String realName)
			throws NickNameException, PasswordCheckException, BadDateException, UserEntityException, FieldException {
		this(userName, password, UserHelper.stringToDate(birthDate), realName);
	}

	/**
	 * Constructor for JPA
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
	 * Add topic to list of topics.
	 * 
	 * @param Topic
	 */
	public void addTopic(Topic topic) {
		topics.add(topic);
	}

	/**
	 * Add topics to list of topics.
	 * 
	 * @param list
	 *            of topics to be added
	 */
	public void addTopicList(List<Topic> list) {
		topics.addAll(list);

	}
	
	/**
	 *remove topic from the list of topics.
	 * 
	 * @param Topic
	 */
	public void removeTopic(Topic topic){
		topics.remove(topic);
	}

	/**
	 * Getter for userName
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for userName
	 * 
	 * @param userName
	 * @throws NickNameException
	 * @throws FieldException 
	 */
	public void setUserName(String userName) throws NickNameException, FieldException {
		testNotEmpty(userName, "user name", true);
		UserHelper.nickNameValidator(userName);
		this.userName = userName;
	}

	/**
	 * Setter for password
	 * 
	 * @param password
	 * @throws PasswordCheckException
	 * @throws FieldException 
	 */
	public void setPassword(String password) throws PasswordCheckException, FieldException {
		testNotEmpty(password, "password", true);
		UserHelper.passwordOverallControll(password);
		this.password = password;
	}

	/**
	 * Getter for birthDate
	 * 
	 * @return birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter for birthDate
	 * 
	 * @param birthDate
	 * @throws FieldException 
	 * @throws BadDateException 
	 */
	public void setBirthDate(Date birthDate) throws FieldException, BadDateException {
		testNotEmpty(birthDate, "birth date", false);
		if(birthDate.after(new Date()))
			throw new BadDateException("Birthday cant be after actual date.");
		this.birthDate = birthDate;
	}

	/**
	 * Setter for birthDate This setter parses string to date and saves (if
	 * conversion is sucess)
	 * 
	 * @param birthDate
	 * @throws BadDateException
	 * @throws FieldException 
	 */
	public void setBirthDate(String birthDate) throws BadDateException, FieldException {
		testNotEmpty(birthDate, "birth date", false);
		setBirthDate(UserHelper.stringToDate(birthDate));
	}

	/**
	 * Getter for role
	 * 
	 * @return role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * Setter for role
	 * 
	 * @param role
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Getter for real name
	 * 
	 * @return real name
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Setter for realName
	 * 
	 * @param RealName
	 * @throws FieldException 
	 */
	public void setRealName(String realName) throws FieldException {
		testNotEmpty(realName, "real name", true);
		// TODO !!!!!! CHECK REAL NAME FOR SPECIAL CHARS
		this.realName = realName;
	}

	/**
	 * Check passwords
	 * 
	 * @param password
	 * @return boolean
	 */
	public boolean authentificate(String password) {
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("User [userName=%s, password=%s, birthDate=%s, realName=%s, role=%s, topics=%s]", userName,
				password, birthDate, realName, role, topics);
	}

	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
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
	 * @param User
	 * @return integer
	 */
	@Override
	public int compareTo(User user) {
		return this.userName.compareTo(user.getUserName());
	}
	
	@Override
	public final void setBlocked(Blocked blocked) throws FieldException {
		if(equals(blocked.getBlockedBy()))
			throw new RuntimeException("YOU CAND DO THIS ANYTIME!!!!!!!!!!!!!!!!!!! RYS(Z)AAA");
		super.setBlocked(blocked);
	}
	
}
