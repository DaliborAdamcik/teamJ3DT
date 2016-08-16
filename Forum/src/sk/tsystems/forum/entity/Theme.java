package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.dto.ThemeObjectDTO;
import sk.tsystems.forum.helper.exceptions.CommonEntityException;

@Entity
@Table(name = "THEME")
public class Theme extends BlockableEntity implements Comparable<Theme> {

	/**
	 * name of the theme
	 * 
	 */
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	/**
	 * theme
	 */
	@ManyToOne
	@JoinColumn(name = "TOPICID")
	private Topic topic;

	/**
	 * description of theme
	 */
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	/**
	 * author
	 */
	//@Column(name = "AUTHOR", nullable = false)
	@ManyToOne
	private User author;

	/**
	 * Public status for theme
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	public Theme(String name, Topic topic, String description, User author, boolean isPublic) throws CommonEntityException {
		this();
		if(name==null || topic==null || description==null || author==null)
			throw new CommonEntityException("Parameters in constructor cant be null", new NullPointerException("Someone didnt work properly."));
			
		setName(name);
		setTopic(topic);
		setDescription(description);
//		setRating(0);
		setAuthor(author);
		setPublic(isPublic);
	}

	/**
	 * Constructor for JPA
	 */
	private Theme() {
		super();
	}

	/**
	 * Getter for name
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
	 * Getter for theme
	 * 
	 * @return theme
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * Setter for theme
	 * 
	 * @param theme
	 */
	public void setTopic(Topic topic) {
		this.topic= topic;
	}

	/**
	 * Getter for description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for author
	 * 
	 * @return author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Setter for author
	 * 
	 * @param author
	 */
	public void setAuthor(User author) {
		this.author = author;
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
	
	public ThemeObjectDTO getRating() {
		return ThemeObjectDTO.getDTO(this);
	}

	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
	 */	
	public boolean equals(Object object) {
		if (object instanceof Theme) {
			if (this.getId() == ((Theme) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable<Theme>.compareTo
	 * 
	 * @param Theme
	 * @return integer
	 */
	@Override
	public int compareTo(Theme o) {
		return this.name.compareTo(o.getName());
	}
}
