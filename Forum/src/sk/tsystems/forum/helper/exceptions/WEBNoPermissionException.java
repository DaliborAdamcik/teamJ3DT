package sk.tsystems.forum.helper.exceptions;

public class WEBNoPermissionException extends Exception {
	private static final long serialVersionUID = 1L;

	public WEBNoPermissionException() {
		super();
	}

	public WEBNoPermissionException(String arg0) {
		super(arg0);
	}

	public WEBNoPermissionException(Throwable arg0) {
		super(arg0);
	}

	public WEBNoPermissionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public WEBNoPermissionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
