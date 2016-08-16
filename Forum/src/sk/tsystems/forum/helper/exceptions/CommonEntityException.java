package sk.tsystems.forum.helper.exceptions;

public class CommonEntityException extends Exception {
	private static final long serialVersionUID = 1L;

	public CommonEntityException() {
		super();
	}

	public CommonEntityException(String arg0) {
		super(arg0);
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
