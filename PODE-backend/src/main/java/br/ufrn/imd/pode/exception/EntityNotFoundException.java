package br.ufrn.imd.pode.exception;

/**
 * Exceção padrão de quando a entidade não for encontrada no banco
 *
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
