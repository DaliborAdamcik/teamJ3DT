package sk.tsystems.forum.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "THEME")
public class Theme  extends BlockableEntity {

	////////////////////////////////////////////////////////////////////////////////
	// OBJECTS
	/**
	 * ID. Generated automatically by Hibernate
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * Name of the theme
	 * 
	 */
	@Column(name = "NAME", nullable = false)
	private String name;

	/**
	 * Public status for theme
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	/**
	 * List of topics
	 */
	@OneToMany
	private List<Topic> topics;

	/**
	 * Creation date. Generated by current date in constructor
	 */
	@Column(name = "CREATIONDATE", nullable = false)
	private Date creationDate;

	////////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	/**
	 * Theme constructor
	 * 
	 * @param name
	 * @param isPublic
	 */
	public Theme(String name, boolean isPublic) {
		super();
		this.name = name;
		this.isPublic = isPublic;
		this.creationDate = new Date();
	}
	
	/**
	 * Non-Parametric constructor used by HIBERNATE
	 * (INFO: HHH000182: No default (no-argument) constructor for class: 
	 * 	sk.tsystems.forum.entity.Theme (class must be instantiated by Interceptor))
	 */
	@Deprecated
	public Theme()
	{
		this(null, true);
	}

	////////////////////////////////////////////////////////////////////////////////
	// GETTERS / SETTERS

	/**
	 * Getter for theme name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for isPublic
	 * 
	 * @return isPublic
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
	 * 
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Getter for topics
	 * 
	 * @return topics
	 */
	public List<Topic> getTopics() {
		return topics;
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
