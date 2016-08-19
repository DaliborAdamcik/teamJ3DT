package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.helper.exceptions.FieldException;

@Entity
@Table(name = "TOPIC")
public class Topic extends BlockableEntity implements Comparable<Topic>{

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
	 * @throws FieldException 
	 */
	public Topic(String name, boolean isPublic) throws FieldException {
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
	 * @throws FieldException 
	 */
	public void setName(String name) throws FieldException {
		testNotEmpty(name, "name", true);
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
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
	 */	
	public boolean equals(Object object) {
		if (object instanceof Topic) {
			if (this.getId() == ((Topic) object).getId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Implements Comparable<Topic>.compareTo
	 * 
	 * @param Topic
	 * @return integer
	 */
	@Override
	public int compareTo(Topic o) {
		return this.name.compareTo(o.getName());
	}
}
