package sk.tsystems.forum.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "THEME")
public class Theme  extends BlockableEntity {

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
	 * Theme constructor
	 * 
	 * @param name
	 * @param isPublic
	 */
	public Theme(String name, boolean isPublic) {
		this();
		setName(name);
		setPublic(isPublic);
	}
	
	/**
	 * Non-Parametric constructor used by HIBERNATE
	 * (INFO: HHH000182: No default (no-argument) constructor for class: 
	 * 	sk.tsystems.forum.entity.Theme (class must be instantiated by Interceptor))
	 */
	public Theme()
	{
		super();
	}

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
	 * <p>This method returns date of Theme create</p>
	 * <p><b><i>DEPRECATED</i></b> Please use getCreated() instead.</p>
	 * @return creationDate (return value is same as getCreated)
	 */
	@Deprecated
	public Date getCreationDate() {
		return getCreated();
	}

	/**
	 * Getter for topics
	 * 
	 * @return topics
	 */
	public List<Topic> getTopics() {
		return topics;
	}
}
