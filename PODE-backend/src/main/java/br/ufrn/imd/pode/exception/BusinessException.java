package br.ufrn.imd.pode.exception;

/**
 * Exceção padrão para todos os problemas de regra de negócio que forem lançadas pela API
 */
public class BusinessException extends RuntimeException {
	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}
}
