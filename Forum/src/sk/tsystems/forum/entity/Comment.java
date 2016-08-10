package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "COMMENTARY")
public class Comment extends BlockableEntity {

	/**
	 * comment
	 */
	@Column(name = "COMMENTARY", nullable = false)
	private String comment;

	/**
	 * topic
	 */
	@ManyToOne
	@JoinColumn(name = "TOPICID")
	private Topic topic;

	/**
	 * owner
	 */
	@ManyToOne
	@JoinColumn(name = "USERID")
	private User owner;

	/**
	 * public status
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	/**
	 * constructor
	 * 
	 * @param comment
	 * @param topic
	 * @param owner
	 * @param isPublic
	 */
	public Comment(String comment, Topic topic, User owner, boolean isPublic) {
		this();
		setComment(comment);
		setTopic(topic);
		setPublic(isPublic);
		this.owner = owner;
	}

	/**
	 * constructor only for JPA.
	 */
	private Comment() {
		super();
	}

	/**
	 * Getter for comment
	 * 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter for comment
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Getter for topic
	 * 
	 * @return topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * Setter for topic
	 * 
	 * @param isPublic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * Getter for owner
	 * 
	 * @return owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Getter for public
	 * 
	 * @return public
	 */
	public boolean isIsPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * 
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Getter for creationDate
	 * <p>This method returns date of Theme create</p>
	 * <p><b><i>DEPRECATED</i></b> Please use getCreated() instead.</p>
	 * @return creationDate (return value is same as getCreated)
	 */
	@Deprecated
	public Date getCreationDate() {
		return getCreated();
	}

}
