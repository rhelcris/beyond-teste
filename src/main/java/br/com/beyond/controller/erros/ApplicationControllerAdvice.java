package br.com.beyond.controller.erros;

import br.com.beyond.service.exception.AccountHasNoEnoughFundsException;
import br.com.beyond.service.exception.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(AccountHasNoEnoughFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleAccountNotFoundException(AccountHasNoEnoughFundsException ex) {
        return new ApiErrors(ex);
    }

}
