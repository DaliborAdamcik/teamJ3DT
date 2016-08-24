package sk.tsystems.forum.helper.exceptions;

public class GetServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GetServiceException() {
		
	}

	public GetServiceException(String message) {
		super(message);
		
	}

	public GetServiceException(Throwable cause) {
		super(cause);
		
	}

	public GetServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public GetServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
