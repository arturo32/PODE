package br.ufrn.imd.pode.exception;

/**
 * Exceção padrão de quando a entidade não for encontrada no banco
 */
public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = -1308366825991170415L;

	public EntidadeNaoEncontradaException() {
	}

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}
	
}
