package sk.tsystems.forum.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Topic {
	@Id
	@GeneratedValue
	int topicId;
	@Column(name="NAME", nullable= false)
	String name;
	@Column(name="PUBLIC", nullable= false)
	boolean isPublic;
	@OneToMany
	List<Comment> comments;
	@Column(name="CREATIONDATE", nullable= false)
	Date creationDate;
	@OneToOne
	Blocked blocked;
}
