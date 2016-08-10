package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENTARY")
public class Comment {
	@Id
	@GeneratedValue
	int id;
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

	public Comment(String comment, Topic topic, User owner, boolean isPublic) {
		super();
		this.comment = comment;
		this.topic = topic;
		this.owner = owner;
		this.isPublic = isPublic;
		this.creationDate = new Date(); // TODO overit ci tento date davca
										// aktualny datum a cas
	}

	@Deprecated
	public Comment() { // Tento konstruktor nepouziva programator , iba jpa
		this(null, null, null, false);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getOwner() {
		return owner;
	}

	public boolean isIsPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Blocked getBlocked() {
		return blocked;
	}

	public void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}

	public int getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
