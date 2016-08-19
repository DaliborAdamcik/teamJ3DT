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
