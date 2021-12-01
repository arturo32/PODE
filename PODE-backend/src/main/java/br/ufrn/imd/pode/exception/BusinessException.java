package br.ufrn.imd.pode.exception;

/**
 * Exceção padrão para todos os problemas de regra de negócio que forem lançadas
 * pela API
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 4986033354705167054L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

}
