package candrun.exception;

public class PreparedStatementException extends RuntimeException {

	private static final long serialVersionUID = -7574860723007401052L;

	public PreparedStatementException() {
		super();
	}

	public PreparedStatementException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PreparedStatementException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreparedStatementException(String message) {
		super(message);
	}

	public PreparedStatementException(Throwable cause) {
		super(cause);
	}

}
