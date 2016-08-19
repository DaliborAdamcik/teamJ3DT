package sk.tsystems.forum.entity.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import sk.tsystems.forum.entity.exceptions.field.FieldException;

/**
 * Common entity properties class
 * This is superclass for all entities
 * Implements common properties, "shared" by all entities  
 * @author Dalibor
 */
@MappedSuperclass
public abstract class CommonEntity {
	
	/**
	 * Entity ID. 
	 * Generated by hibernate
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private final int id;
	
	/**
	 * Creation date
	 * Date and time of entity create
	 */
	@Column(name = "CREATEDATE", nullable = false)
	private final Date created;


	/**
	 * Modify date
	 * Date and time of last modify
	 */
	@Column(name = "MODIFYDATE", nullable = false)
	private Date modified;
	
	/**
	 * Default constructor for Common Entity
	 * This constructor assigns creation time + date to current time + date
	 */
	protected CommonEntity() {
		super();
		this.id = 0;
		this.created = new Date();
		this.modified = this.created;
	}

	/**
	 * Sets modified {@link Date} to current date/time
	 */
	@PreUpdate
    private void setLastModified() {
		this.modified = new Date();
    }	
	
	/**
	 * Getter for ID
	 * Gets an unique ID of entity.
	 * <p>An <b>non - persisted entity<b> has assigned ID value of zero <i>(ID = 0)</i>.</p>
	 * <p>An <b>persisted entity<b> has assigned ID value higher than zero <i>(ID > 0)</i>.</p>
	 * <p>Value is assigned to entity at fist persist. It wouldn'd be changed.</p>
	 * <b>This method cannot be overriden</b>
	 * @return an unique ID for persisted entity ( ID > 0) otherwise zero value (0) 
	 */	
	public final int getId() {
		return id;
	}

	/**
	 * Getter for Date of entity creation
	 * <p>An <b>non - persisted entity<b> has assigned current date and time on creation.</i></p>
	 * <p>An <b>persisted entity<b> has assigned an stored date and time of first creation.</i></p>
	 * <p>Value is assigned to entity at fist create. It wouldn'd be changed.</p>
	 * @return Date and time assigned to entity on creation
	 */
	public final Date getCreated() {
		return created;
	}

	/**
	 * Getter for Date of entity last modification
	 * <p>An <b>non - persisted entity<b> has assigned current date and time on creation.</i></p>
	 * <p>An <b>persisted entity<b> has assigned an stored date and time of persistence call.</i></p>
	 * <p>Value is changing after persistence. </p>
	 * @return Date and time of last modification
	 */
	public Date getModified() {
		return modified;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommonEntity) 
			return this.getId() == ((CommonEntity) obj).getId();
		
		return false;
	}
	
	/** Max string length */
	@Transient
	private final int MAX_STR_LEN = 255;

	/** Max string length for text */
	@Transient
	private final int MAX_TXT_LEN = 4096;

	/**
	 * Checks object (field) is empty.
	 * In case field is String, also is checked for empty string.
	 * Use this function in entity setters / constructors. 
	 * @param valToCheck {@link Object} An object to be checked
	 * @param fieldName {@link String} description (name of field) for exception
	 * @param maxLen {@link Boolean} checks {@link String} length true = MAX_STR_LEN characters false = MAX_TXT_LEN characters
	 * @throws FieldException
	 */
	protected void testNotEmpty(Object valToCheck, String fieldName, boolean maxLen) throws FieldException {
		if(valToCheck==null)
			throw new FieldException(String.format(FieldException.EMPTY_FIELD_MSG, fieldName, getClass().getSimpleName(), "be null"));

		if(valToCheck instanceof String)
		{
			int len = ((String) valToCheck).trim().length(); 
			if(len ==0)
				throw new FieldException(String.format(FieldException.EMPTY_FIELD_MSG, fieldName, getClass().getSimpleName(), "be empty"));

			if(maxLen && len > MAX_STR_LEN)
				throw new FieldException(String.format(FieldException.EMPTY_FIELD_MSG, fieldName, getClass().getSimpleName(), "have length over "+MAX_STR_LEN+" characters"));

			if(!maxLen && len > MAX_TXT_LEN)
				throw new FieldException(String.format(FieldException.EMPTY_FIELD_MSG, fieldName, getClass().getSimpleName(), "have length over "+MAX_STR_LEN+" characters"));
		}
	}
	
}