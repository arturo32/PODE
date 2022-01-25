package br.ufrn.imd.app1.exception;

public class EntidadeInconsistenteException extends NegocioException {

	private static final long serialVersionUID = 4089157898231692806L;

	public EntidadeInconsistenteException() {

	}

	public EntidadeInconsistenteException(String message) {
		super(message);
	}

}
