package candrun.exception;

public class PasswordMismatchException extends Exception {
	private static final long serialVersionUID = -2183427797862106234L;

	public PasswordMismatchException() {
		super();
	}

	public PasswordMismatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordMismatchException(String message) {
		super(message);
	}

	public PasswordMismatchException(Throwable cause) {
		super(cause);
	}

}
