package br.ufrn.imd.pode.exception;

public class UnmetPrerequisitesException extends BusinessException {

	private static final long serialVersionUID = 4986033354705167054L;

	public UnmetPrerequisitesException() {
	}

	public UnmetPrerequisitesException(String message) {
		super(message);
	}
}
