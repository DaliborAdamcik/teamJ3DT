package sk.tsystems.forum.helper.exceptions;
/**
 * An exception for URLParser object
 * This exception indicates, an URL cant be parsed (unknown format)
 * @author Dalibor
 */
public class URLParserException extends Exception {
	private static final long serialVersionUID = 1L;

	public URLParserException() {
		super();
	}

	public URLParserException(String arg0) {
		super(arg0);
	}

	public URLParserException(Throwable arg0) {
		super(arg0);
	}

	public URLParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public URLParserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
