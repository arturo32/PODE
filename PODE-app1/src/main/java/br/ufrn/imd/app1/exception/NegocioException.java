package br.ufrn.imd.app1.exception;

/**
 * Exceção padrão para todos os problemas de regra de negócio que forem lançadas
 * pela API
 */
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 4986033354705167054L;

	public NegocioException() {
	}

	public NegocioException(String message) {
		super(message);
	}

}
