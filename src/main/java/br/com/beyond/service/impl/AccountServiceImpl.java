package br.com.beyond.service.impl;

import br.com.beyond.domain.Account;
import br.com.beyond.repository.IAccountRepository;
import br.com.beyond.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final IAccountRepository iAccountRepository;

    @Autowired
    public AccountServiceImpl(IAccountRepository iAccountRepository) {
        this.iAccountRepository = iAccountRepository;
    }

    @Override
    public Optional<Account> getAccountById(int id) {
        return iAccountRepository.getAccountById(id);
    }

    @Override
    public Account update(Account account) {
        return iAccountRepository.save(account);
    }

}
