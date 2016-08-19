package sk.tsystems.forum.helper.exceptions;

/**
 * An common exception in entity
 * Exceptions thrown in entity must implement this class
 * @author Dalibor Adamcik
 */
public class CommonEntityException extends Exception {
	private static final long serialVersionUID = 2L;

	public CommonEntityException() {
		super();
	}

	public CommonEntityException(String message) {
		super(message);
	}

	public CommonEntityException(Throwable cause) {
		super(cause);
	}

	public CommonEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
