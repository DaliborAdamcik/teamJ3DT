package sk.tsystems.forum.helper.exceptions;

/***
 * This exception is thrown in general check of field values. 
 * @author Dalibor Adamcik
 */
public class EmptyFieldException extends CommonEntityException {
	private static final long serialVersionUID = 1L;

	public EmptyFieldException() {
		super();
	}

	public EmptyFieldException(String message) {
		super(message);
	}

	public EmptyFieldException(Throwable cause) {
		super(cause);
	}

	public EmptyFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/** Field '%s' at entity '%s' can't %s. */
	public static String EMPTY_FIELD_MSG = "Field '%s' at entity '%s' can't %s.";
}
