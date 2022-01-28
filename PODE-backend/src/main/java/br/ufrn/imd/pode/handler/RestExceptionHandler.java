package br.ufrn.imd.pode.handler;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.NegocioException;
import org.hibernate.JDBCException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe de manipulação dos erros lançados e construção de uma resposta mais
 * legível para o cliente.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@NotNull
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex,
	                                                              @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Problemas de formação do JSON"));
	}

	/**
	 * Este método manipula os erros lançados como EntityNotFound e constrói o
	 * retorno tratado com o status lançado como 404 e a mensagem obtida do erro.
	 *
	 * @param entidadeNaoEncontradaException EntityNotFoundException: erro capturado
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<Object> handlerExceptionEntityNotFound(EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, this.getSimpleMessage(entidadeNaoEncontradaException));

		return buildResponseEntity(apiError);
	}

	/**
	 * Este método manipula os erros lançados como BusinessException e constrói o
	 * retorno tratado com o status lançado como 400 e a mensagem obtida do erro.
	 *
	 * @param negocioException BusinessException: erro capturado
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler(NegocioException.class)
	protected ResponseEntity<Object> handlerExceptionBusiness(NegocioException negocioException) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, this.getSimpleMessage(negocioException));

		return buildResponseEntity(apiError);
	}

	/**
	 * Este método manipula os erros lançados como erros de banco JDBCException e
	 * constrói o retorno tratado com o status lançado como 500 e a mensagem obtida
	 * do erro.
	 *
	 * @param jdbcException JDBCException: erro capturado
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler(JDBCException.class)
	protected ResponseEntity<Object> handlerExceptionConstraintViolation(JDBCException jdbcException) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, this.getJDBCExceptionMessage(jdbcException));
		apiError.getApiSpecificErrors().add(this.getApiSpecificErrorJDBCException(jdbcException));

		return buildResponseEntity(apiError);
	}

	/**
	 * Este método constrói o retorno do tratamento da exceção a partir do objeto
	 * ApiError informado.
	 *
	 * @param apiError ApiError: objeto formado a partir dos erros capturados.
	 * @return ResponseEntity<Object>
	 */
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}

	/**
	 * Método simples que forma a mensagem que o usuário lerá. Esse método deve ser
	 * chamado quando as exceções são construídas pelo programador e sua mensagem é
	 * personalizada por ele.
	 *
	 * @param exception Throwable: exceção lançada que contém a mensagem.
	 * @return String
	 */
	private String getSimpleMessage(Throwable exception) {
		if (StringUtils.isEmpty(exception.getMessage())) {
			return "Mensagem não informada";
		}
		return exception.getMessage();
	}

	/**
	 * Método que forma a mensagem que o usuário lerá a partir de exceções lançadas
	 * do tipo JDBCException
	 *
	 * @param jdbcException JDBCException: exceção lançada
	 * @return String
	 */
	private String getJDBCExceptionMessage(JDBCException jdbcException) {
		String[] n = jdbcException.getCause().getMessage().split("Detalhe: ");

		if (n.length < 2) {
			n = jdbcException.getCause().getMessage().split("Detail: ");
		}

		return n[n.length - 1];
	}

	/**
	 * Constrói um objeto ApiSpecificError a partir das informações lançadas na
	 * exceção JDBCException
	 *
	 * @param jdbcException JDBCException: exceção lançada
	 * @return ApiSpecificError
	 */
	private ApiSpecificError getApiSpecificErrorJDBCException(JDBCException jdbcException) {
		ApiSpecificError apiSpecificError = new ApiSpecificError("JDBCException");
		apiSpecificError.getDebugMessage().add("Debug Message: " + jdbcException.getMessage());
		apiSpecificError.getDebugMessage().add("SQL: " + jdbcException.getSQL());
		apiSpecificError.getDebugMessage().add("Cause Message: " + jdbcException.getCause().getMessage());

		return apiSpecificError;
	}
}
