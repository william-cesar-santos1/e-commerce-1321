package br.ada.ecommerce.port.web;

import br.ada.ecommerce.port.controllers.product.ProductAlreadyExists;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleBattleException(
            EntityNotFoundException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        var body = Map.of(
                "code", HttpStatus.NOT_FOUND,
                "message", "Not found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        var errors = new ArrayList<Map<String, String>>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(Map.of(error.getField(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(Map.of(error.getObjectName(), error.getDefaultMessage()));
        }

        var body = Map.of(
                "code", HttpStatus.BAD_REQUEST,
                "errors", errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = {ProductAlreadyExists.class})
    public ResponseEntity<Object> handleProductAlreadyExists(
            ProductAlreadyExists ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "code": "ERR_00001",
                            "message": "Product already exists"
                        }
                        """);
    }
}