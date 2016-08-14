package sk.tsystems.forum.helper.exceptions;

public class UnknownActionException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnknownActionException() {
		super();
	}

	public UnknownActionException(String arg0) {
		super(arg0);
	}

	public UnknownActionException(Throwable arg0) {
		super(arg0);
	}

	public UnknownActionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UnknownActionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
