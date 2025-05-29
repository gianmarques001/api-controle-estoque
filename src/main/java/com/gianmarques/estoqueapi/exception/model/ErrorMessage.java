package com.gianmarques.estoqueapi.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private String path;

    private String method;

    private Integer statusCode;

    private String statusText;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErros(bindingResult);
    }


    private void addErros(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errors.put("field", fieldError.getField());
            errors.put("message", fieldError.getDefaultMessage());
        });

    }

    public Integer getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }


}
