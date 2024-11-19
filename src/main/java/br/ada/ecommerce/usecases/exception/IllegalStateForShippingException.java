package br.ada.ecommerce.usecases.exception;

public class IllegalStateForShippingException extends RuntimeException {

    public IllegalStateForShippingException(String message) {
        super(message);
    }
}
