package br.ada.ecommerce.usecases.repository;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String message, Exception cause) {
        super(message, cause);
    }

}
