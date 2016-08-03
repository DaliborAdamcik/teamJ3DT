package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	int commentid;
	@Column(name = "COMMENTARY", nullable = false)
	String comment;
	@ManyToOne
	@JoinColumn(name = "TOPICID")
	Topic topic;
	@Column(name = "CREATIONDATE", nullable = false)
	Date creationDate;
	@ManyToOne
	@JoinColumn(name = "USERID")
	User owner;
	@Column(name = "ISPUBLIC", nullable = false)
	boolean isPublic;
	@OneToOne
	Blocked blocked;
}
