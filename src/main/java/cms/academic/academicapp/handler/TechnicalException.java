package cms.academic.academicapp.handler;

public class TechnicalException extends ApplicationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TechnicalException() {
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}
