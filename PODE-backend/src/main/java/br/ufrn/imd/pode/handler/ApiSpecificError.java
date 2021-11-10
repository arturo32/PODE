package br.ufrn.imd.pode.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe padrão de formatação de informações mais específicas das exceções lançadas
 * a fim de uma análise mais profunda pelo programador.
 *
 */
public class ApiSpecificError {
    // Tipo da exceção lançada
    private String errorType;

    // Lista de mensagens de erro que deseja-se que o programador saiba
    private List<String> debugMessage;

    public ApiSpecificError() {
        this.debugMessage = new ArrayList<>();
    }

    public ApiSpecificError(String errorType) {
        this.errorType = errorType;
        this.debugMessage = new ArrayList<>();
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<String> getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(List<String> debugMessage) {
        this.debugMessage = debugMessage;
    }
}
