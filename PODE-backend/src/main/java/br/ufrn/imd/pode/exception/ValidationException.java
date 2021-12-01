package br.ufrn.imd.pode.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 4708225233399249765L;

	public ValidationException() {

	}

	public ValidationException(String message) {
		super(message);
	}

}
