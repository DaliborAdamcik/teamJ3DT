package sk.tsystems.forum.entity.exceptions.field;

import sk.tsystems.forum.entity.common.CommonEntity;

/**
 * <b>Exception for checking field values</b><br>
 * (as SuperException for entity)<br>
 * Extends {@link FieldException}
 */
public class FieldValueException extends FieldException {
	private static final long serialVersionUID = 1L;

	public FieldValueException(String message) {
		super(message);
	}

	public FieldValueException(String message, Throwable cause) {
		super(message, cause);
	}

	/** Common exception message format: Field '%s' at entity '%s' can't %s. */
	public static String EMPTY_FIELD_MSG = "Field '%s' at entity '%s' can't %s.";
	
	/**
	 * Checks object (field) is empty.
	 * In case field is String, also is checked for empty string.
	 * Use this function in entity setters / constructors. 
	 * @param valToCheck {@link Object} An object to be checked
	 * @param fieldName {@link String} description (name of field) for exception
	 * @param maxLen {@link Boolean} checks {@link String} length true = MAX_STR_LEN characters false = MAX_TXT_LEN characters
	 * @throws FieldValueException
	 */
	
	/**
	 * This throw exception with specified message format<br>
	 * Message: <i>Field '[fieldName]' at entity '[class name]' can't [description].</i><br>
	 * Example usage: 
	 * FieldValueException(this, "user name", "be empty");
	 *  
	 * @param causedIn {@link CommonEntity} An entity which caused exception
	 * @param fieldName {@link String} An name of field
	 * @param description {@link String} description for cause of exception
	 */
	public FieldValueException(CommonEntity causedIn, String fieldName, String description) {
		super(String.format("Field '%s' at entity '%s' can't %s.", fieldName, causedIn.getClass().getSimpleName(),
				description));
	}
	
}
