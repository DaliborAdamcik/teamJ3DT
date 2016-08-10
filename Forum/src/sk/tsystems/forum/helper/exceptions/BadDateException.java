package sk.tsystems.forum.helper.exceptions;

public class BadDateException extends Exception {

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