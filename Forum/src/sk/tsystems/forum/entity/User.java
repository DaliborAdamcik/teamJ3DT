package sk.tsystems.forum.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "JPA_USER")
public class User extends BlockableEntity {
	/**
	 * ID. Generated by hibernate
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * User Name
	 */

	@Column(name = "USERNAME", nullable = false, unique=true)
	private String userName;

	/**
	 * Password
	 */
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	/**
	 * Birth Date (java.util.date)
	 */
	@Column(name = "BIRTHDATE", nullable = false)
	private Date birthDate;

	/**
	 * Registration date. Generated by current date in constructor.
	 */
	@Column(name = "REGISTRATIONDATE", nullable = false)
	private Date registrationDate;

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
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Topic> topics;

	// /**
	// * One-To-Many connection between user and comments
	// */
	// @OneToMany
	// List<Comment> comments; // TODO implementovat list ak nam ho bude treba
	

	public User(String userName, String password, Date birthDate, String realName) {
		super();
		topics = new ArrayList<Topic>();
		this.userName = userName;
		this.password = password;
		this.birthDate = birthDate;
		this.registrationDate = new Date();
		this.realName = realName;
		this.role = UserRole.GUEST;
	}

	@Deprecated
	public User() {
		this(null, null, null, null);
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
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
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
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
	 * Getter for registrationDate
	 * 
	 * @return registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
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
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Getter for ID
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

}
