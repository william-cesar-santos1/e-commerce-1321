package br.ada.ecommerce.application.usecases.exception;

public class UseCaseException extends RuntimeException{

    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
