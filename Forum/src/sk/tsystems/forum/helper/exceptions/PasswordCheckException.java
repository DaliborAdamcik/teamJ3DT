package sk.tsystems.forum.helper.exceptions;

import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;

/**
 * PasswordCheckException
 * Auto-generated code 
 * @author Dalibor
 */
public class PasswordCheckException extends UserEntityFieldException {
	private static final long serialVersionUID = 1L;

	public PasswordCheckException() {
	}

	public PasswordCheckException(String message) {
		super(message);
	}

	public PasswordCheckException(Throwable cause) {
		super(cause);
	}

	public PasswordCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordCheckException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
