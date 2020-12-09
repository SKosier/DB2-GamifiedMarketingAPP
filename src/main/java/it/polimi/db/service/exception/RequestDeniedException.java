package it.polimi.db.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class RequestDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestDeniedException(String message) {
		super(message);
	}

	public RequestDeniedException() {
		super();
	}

	public RequestDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RequestDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestDeniedException(Throwable cause) {
		super(cause);
	}
}
