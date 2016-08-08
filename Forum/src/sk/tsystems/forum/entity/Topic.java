package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TOPIC")
public class Topic {
	/**
	 * ID. Generated automatically by Hibernate
	 */
	@Id
	@GeneratedValue
	int id;
	
	/**
	 * name of the topic
	 * 
	 */
	@Column(name = "NAME", nullable = false)
	String name;
	
	/**
	 * Public status for topic
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	boolean isPublic;
	// TODO implementovat list kommentov ak nam ho bude treba
	// @OneToMany
	// List<Comment> comments;
	
	/**
	 * Creation date. Generated by current date in constructor
	 */
	@Column(name = "CREATIONDATE", nullable = false)
	Date creationDate;
	
	/**
	 * Blocked
	 */
	@OneToOne
	Blocked blocked;

	public Topic(String name, boolean isPublic) {
		super();
		this.name = name;
		this.isPublic = isPublic;
		this.creationDate = new Date();

	}

	@Deprecated
	public Topic() {
		this(null, false);
	}
/**
 * Getter for name
 * @return name
 */
	public String getName() {
		return name;
	}
	/**
	 * Setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter for isPublic
	 * @return isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	/**
	 * Getter for creationDate
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * Getter for blocked
	 * @return blocked
	 */
	public Blocked getBlocked() {
		return blocked;
	}
	/**
	 * Setter for blocked
	 * @param Blocked
	 */
	public void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}
	/**
	 * Getter for ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

}
