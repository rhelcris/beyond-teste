package br.com.beyond.controller.erros;

import br.com.beyond.service.exception.AccountHasNoEnoughFundsException;
import br.com.beyond.service.exception.AccountNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach( error -> this.errors.add(error.getDefaultMessage()) );
    }

    public ApiErrors(AccountNotFoundException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }
    public ApiErrors(AccountHasNoEnoughFundsException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }

    public ApiErrors(ResponseStatusException ex) {
        this.errors = Arrays.asList(ex.getReason());
    }

    public List<String> getErrors() {
        return errors;
    }

}
