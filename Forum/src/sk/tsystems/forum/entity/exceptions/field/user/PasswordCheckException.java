package sk.tsystems.forum.entity.exceptions.field.user;

/**
 * <b>Exception for checking password field.</b><br>
 * Extends {@link UserEntityFieldException}
 */
public class PasswordCheckException extends UserEntityFieldException {
	private static final long serialVersionUID = 1L;

	public PasswordCheckException(String message) {
		super(message);
	}

	public PasswordCheckException(String message, Throwable cause) {
		super(message, cause);
	}
}
