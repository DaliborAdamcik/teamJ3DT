package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name="COMMENTARY")
public class Comment  extends BlockableEntity {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "COMMENTARY", nullable = false)
	private String comment;
	@ManyToOne
	@JoinColumn(name = "TOPICID")
	private Topic topic;
	@Column(name = "CREATIONDATE", nullable = false)
	private Date creationDate;
	@ManyToOne
	@JoinColumn(name = "USERID")
	private User owner;
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

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

	public int getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
