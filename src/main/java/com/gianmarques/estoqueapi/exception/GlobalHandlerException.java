package com.gianmarques.estoqueapi.exception;


import com.gianmarques.estoqueapi.exception.exceptions.EmailUnicoException;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.exception.exceptions.LoginInvalidoException;
import com.gianmarques.estoqueapi.exception.exceptions.ProdutoDuplicadoException;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailUnicoException.class)
    public ResponseEntity<ErrorMessage> handleEmailUniqueException(EmailUnicoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorMessage> handleEmailUniqueException(EntidadeNaoEncontradaException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(ProdutoDuplicadoException.class)
    public ResponseEntity<ErrorMessage> handleEmailUniqueException(ProdutoDuplicadoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult result) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Alguns campos estão invalidos", result));
    }

    @ExceptionHandler(LoginInvalidoException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(LoginInvalidoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, e.getMessage()));
    }


}
