package sk.tsystems.forum.entity.exceptions.field;

/***
 * This exception is thrown in general check of field values. 
 * @author Dalibor Adamcik
 */
public class FieldValueException extends FieldException {
	private static final long serialVersionUID = 1L;

	public FieldValueException() {
		super();
	}

	public FieldValueException(String message) {
		super(message);
	}

	public FieldValueException(Throwable cause) {
		super(cause);
	}

	public FieldValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/** Field '%s' at entity '%s' can't %s. */
	public static String EMPTY_FIELD_MSG = "Field '%s' at entity '%s' can't %s.";
}
