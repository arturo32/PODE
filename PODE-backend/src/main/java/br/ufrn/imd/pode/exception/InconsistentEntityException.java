package br.ufrn.imd.pode.exception;

public class InconsistentEntityException extends RuntimeException {

	private static final long serialVersionUID = 4089157898231692806L;

	public InconsistentEntityException() {

	}

	public InconsistentEntityException(String message) {
		super(message);
	}

}
