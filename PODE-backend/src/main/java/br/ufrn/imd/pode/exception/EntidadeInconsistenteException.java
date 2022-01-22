package br.ufrn.imd.pode.exception;

public class EntidadeInconsistenteException extends NegocioException {

	private static final long serialVersionUID = 4089157898231692806L;

	public EntidadeInconsistenteException() {

	}

	public EntidadeInconsistenteException(String message) {
		super(message);
	}

}
