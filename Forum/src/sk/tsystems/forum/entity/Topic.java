package sk.tsystems.forum.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "TOPIC")
public class Topic  extends BlockableEntity {

	/**
	 * name of the topic
	 * 
	 */
	@Column(name = "NAME", nullable = false,unique=true)
	private String name;
	
	/**
	 * Public status for topic
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;
	// TODO implementovat list kommentov ak nam ho bude treba
	// @OneToMany
	// List<Comment> comments;
	
	public Topic(String name, boolean isPublic) {
		this();
		setName(name);
		setPublic(isPublic);
	}

	/**
	 * Constructor for JPA
	 */
	private Topic() {
		super();
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
	public boolean isIsPublic() {
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
	 * <p>This method returns date of topic create</p>
	 * <p><b><i>DEPRECATED</i></b> Please use getCreated() instead.</p>

	 * @return creationDate (return value is same as getCreated)
	 */
	@Deprecated
	public Date getCreationDate() {
		return getCreated();
	}
}
