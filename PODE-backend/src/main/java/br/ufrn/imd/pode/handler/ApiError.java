package br.ufrn.imd.pode.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe padrão para as exceções lançadas pela API
 *
 */
public class ApiError {
    // Status que vai ser lançado pela API
    private HttpStatus httpStatus;

    // Data e hora em que a exceção for lançada
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    // Mensagem para o usuário
    private String userMessage;

    // Informações mais específicas do erro para o desenvolvedor
    private List<ApiSpecificError> apiSpecificErrors;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
        this.apiSpecificErrors = new ArrayList<>();
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
        this.apiSpecificErrors = new ArrayList<>();
    }

    public ApiError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.userMessage = "Unexpected error";
        this.apiSpecificErrors = new ArrayList<>();
    }

    public ApiError(HttpStatus httpStatus, String userMessage) {
        this();
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
        this.apiSpecificErrors = new ArrayList<>();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public List<ApiSpecificError> getApiSpecificErrors() {
        return apiSpecificErrors;
    }

    public void setApiSpecificErrors(List<ApiSpecificError> apiSpecificErrors) {
        this.apiSpecificErrors = apiSpecificErrors;
    }
}
