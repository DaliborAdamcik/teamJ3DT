package sk.tsystems.forum.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "TOPIC")
public class Topic extends BlockableEntity {

	/**
	 * Name of the topic
	 * 
	 */
	@Column(name = "NAME", nullable = false)
	private String name;

	/**
	 * Public status for topic
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	/**
	 * Topic constructor
	 * 
	 * @param name
	 * @param isPublic
	 */
	public Topic(String name, boolean isPublic) {
		this();
		setName(name);
		setPublic(isPublic);
	}
	
	/**
	 * Non-Parametric constructor used by HIBERNATE
	 * (INFO: HHH000182: No default (no-argument) constructor for class: 
	 * 	sk.tsystems.forum.entity.Theme (class must be instantiated by Interceptor))
	 */
	private Topic()
	{
		super();
	}

	/**
	 * Getter for topic name
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

}
