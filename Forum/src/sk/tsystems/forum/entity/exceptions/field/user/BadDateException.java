package sk.tsystems.forum.entity.exceptions.field.user;

/**
 * <b>Exception for checking date fields.</b><br>
 * Extends {@link UserEntityFieldException}
 */
public class BadDateException extends UserEntityFieldException {
	private static final long serialVersionUID = 1L;

	public BadDateException(String message) {
		super(message);
	}

	public BadDateException(String message, Throwable cause) {
		super(message, cause);
	}
}
