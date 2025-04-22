package br.ada.ecommerce.application.usecases.exception;

public class IllegalStateForShippingException extends RuntimeException {

    public IllegalStateForShippingException(String message) {
        super(message);
    }
}
