package sk.tsystems.forum.entity.exceptions.field.user;

/**
 * <b>Exception for checking nick name field.</b><br>
 * Extends {@link UserEntityFieldException}
 */
public class NickNameException extends UserEntityFieldException {

	private static final long serialVersionUID = 1L;

	public NickNameException(String message) {
		super(message);
	}

	public NickNameException(String message, Throwable cause) {
		super(message, cause);
	}
}
