package sk.tsystems.forum.entity.exceptions;

/**
 * <b>Common exception for entity</b><br>
 * (as SuperException for entity)<br>
 * Extends {@link Exception}
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
