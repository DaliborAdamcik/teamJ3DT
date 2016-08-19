package sk.tsystems.forum.helper.exceptions;

import sk.tsystems.forum.entity.exceptions.CommonEntityException;

public class UserEntityException extends CommonEntityException {
	private static final long serialVersionUID = 1L;

	public UserEntityException() {
		super();
	}

	public UserEntityException(String message) {
		super(message);

	}

	public UserEntityException(Throwable cause) {
		super(cause);

	}

	public UserEntityException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
