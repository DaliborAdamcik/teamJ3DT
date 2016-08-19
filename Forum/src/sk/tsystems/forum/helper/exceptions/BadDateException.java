package sk.tsystems.forum.helper.exceptions;

import sk.tsystems.forum.entity.exceptions.field.user.UserEntityException;

public class BadDateException extends UserEntityException {
	private static final long serialVersionUID = 1L;

	public BadDateException() {
		super();
	}

	public BadDateException(String message) {
		super(message);

	}

	public BadDateException(Throwable cause) {
		super(cause);

	}

	public BadDateException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BadDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
