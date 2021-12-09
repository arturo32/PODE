package br.ufrn.imd.pode.exception;

public class PrerequisitosNaoAtendidosException extends BusinessException {

	private static final long serialVersionUID = 4986033354705167054L;

	public PrerequisitosNaoAtendidosException() {
	}

	public PrerequisitosNaoAtendidosException(String message) {
		super(message);
	}
}
