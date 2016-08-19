package sk.tsystems.forum.helper.exceptions;

/***
 * This exception is thrown in general check of field values. 
 * @author Dalibor Adamcik
 */
public class FieldException extends CommonEntityException {
	private static final long serialVersionUID = 1L;

	public FieldException() {
		super();
	}

	public FieldException(String message) {
		super(message);
	}

	public FieldException(Throwable cause) {
		super(cause);
	}

	public FieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/** Field '%s' at entity '%s' can't %s. */
	public static String EMPTY_FIELD_MSG = "Field '%s' at entity '%s' can't %s.";
}
