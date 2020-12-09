package it.polimi.db.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class EntityMissingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityMissingException() {
		super();
	}
	
	public EntityMissingException(Class<?> cls, Object ref) {
		super("Entity with reference " + ref + " of " + cls + " not found.");
	}

	public EntityMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityMissingException(String message) {
		super(message);
	}
	public EntityMissingException(Throwable cause) {
		super(cause);
	}
}