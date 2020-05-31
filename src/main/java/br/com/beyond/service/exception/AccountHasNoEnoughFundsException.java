package br.com.beyond.service.exception;

public class AccountHasNoEnoughFundsException extends RuntimeException {

    public AccountHasNoEnoughFundsException(String str) {
        super(str);
    }

}
