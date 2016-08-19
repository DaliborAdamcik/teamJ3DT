package sk.tsystems.forum.entity.exceptions.field.user;

import sk.tsystems.forum.entity.exceptions.field.FieldException;

public class UserEntityFieldException extends FieldException {
	private static final long serialVersionUID = 1L;

	public UserEntityFieldException() {
		super();
	}

	public UserEntityFieldException(String message) {
		super(message);

	}

	public UserEntityFieldException(Throwable cause) {
		super(cause);

	}

	public UserEntityFieldException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserEntityFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
