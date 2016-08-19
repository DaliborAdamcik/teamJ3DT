package sk.tsystems.forum.entity.exceptions.field;

import sk.tsystems.forum.entity.exceptions.CommonEntityException;

/***
 * This exception is <i>common for checking fields in entities</i>.<br>
 * Exception extends {@link CommonEntityException}
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
}
