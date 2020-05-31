package br.com.beyond.service;

import br.com.beyond.domain.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> getAccountById(int id);

    Account update(Account account);

}
