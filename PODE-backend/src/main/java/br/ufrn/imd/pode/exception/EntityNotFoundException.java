package br.ufrn.imd.pode.exception;

/**
 * Exceção padrão de quando a entidade não for encontrada no banco
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1308366825991170415L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message) {
		super(message);
	}
	
}
