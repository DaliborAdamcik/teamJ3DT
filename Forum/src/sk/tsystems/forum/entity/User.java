package sk.tsystems.forum.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@OneToMany
	List<Topic> topics;
	@OneToMany
	List<Comment> comments;
	@OneToOne
	Blocked blocked;
}
