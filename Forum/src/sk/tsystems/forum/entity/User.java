package sk.tsystems.forum.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="JPA_USER")
public class User {
	@Id
	@GeneratedValue
	int userid;
	@Column(name = "USERNAME", nullable = false)
	String userName;
	@Column(name = "PASSWORD", nullable = false)
	String password;
	@Column(name = "BIRTHDATE", nullable = false)
	Date birthDate;
	@Column(name = "REGISTRATIONDATE", nullable = false)
	Date registrationDate;
	@Column(name = "NAME")
	String name;
	@Column(name = "ROLE")
	UserRole role;
	// TODO implementovat list ak nam ho bude treba
	// @OneToMany
	// List<Topic> topics;
	// @OneToMany
	// List<Comment> comments;
	@OneToOne
	Blocked blocked;

	public User(String userName, String password, Date birthDate, String name) {
		super();

		this.userName = userName;
		this.password = password;
		this.birthDate = birthDate;
		this.registrationDate = new Date();
		this.name = name;
		this.role = UserRole.GUEST;
	}
	
	public User(){
		this(null, null, null, null);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Blocked getBlocked() {
		return blocked;
	}

	public void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}

	public int getUserid() {
		return userid;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

}
