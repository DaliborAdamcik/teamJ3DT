package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;

/**
 * Entity for Topic containing {@link String} name of the topic and its public status
 * 
 * @author J3DT
 */
@Entity
@Table(name = "TOPIC")
public class Topic extends BlockableEntity implements Comparable<Topic> {

	/** {@link String} field <b>name of topic</b> */
	@Column(name = "NAME", nullable = false)
	private String name;

	/** boolean field <b>public status of topic</b> */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	/**
	 * Topic constructor
	 * 
	 * @param name
	 *            {@link String} name of topic
	 * @param isPublic
	 *            public status of topic
	 * @throws {@link
	 *             FieldValueException}
	 */
	public Topic(String name, boolean isPublic) throws FieldValueException {
		this();
		setName(name);
		setPublic(isPublic);
	}

	/**
	 * Non-Parametric constructor used by HIBERNATE (INFO: HHH000182: No default
	 * (no-argument) constructor for class: sk.tsystems.forum.entity.Theme
	 * (class must be instantiated by Interceptor))
	 */
	private Topic() {
		super();
	}

	/**
	 * Getter for topic name
	 * 
	 * @return {@link String} name of topic
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name
	 *            {@link String} name of topic
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setName(String name) throws FieldValueException {
		testNotEmpty(name, "name", true);
		this.name = name;
	}

	/**
	 * Getter for isPublic
	 * 
	 * @return boolean public status of topic
	 */
	public boolean isIsPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * 
	 * @param isPublic
	 *            boolean public status of topic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Overrides java.lang.Object.equals
	 *
	 * @param object
	 *            {@link Object}
	 * @return true if <b>this</b> has the same id as {@link Object} in the
	 *         parameter
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
	 * Implements Comparable\<Topic\>.compareTo
	 * 
	 * @param {@link
	 * 			Topic}
	 * @return returned value(integer) is > 0 when the name of topic is higher
	 *         in alphabetical order than the name of the topic in parameter, =
	 *         0 if equal, < 0 if lower
	 */
	@Override
	public int compareTo(Topic o) {
		return this.name.compareTo(o.getName());
	}
}
