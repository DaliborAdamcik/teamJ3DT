package sk.tsystems.forum.entity.exceptions.field.user;

import sk.tsystems.forum.entity.exceptions.field.FieldException;

/**
 * <b>Common exception for checking fields especially for {@link User} entity.</b><br>
 * Extends {@link FieldException}
 */
public class UserEntityFieldException extends FieldException {
	private static final long serialVersionUID = 1L;

	public UserEntityFieldException(String message) {
		super(message);
	}

	public UserEntityFieldException(String message, Throwable cause) {
		super(message, cause);
	}
}
