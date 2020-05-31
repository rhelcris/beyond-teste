package br.com.beyond.service.impl;

import br.com.beyond.domain.Account;
import br.com.beyond.service.AccountService;
import br.com.beyond.service.MoneyTransfer;
import br.com.beyond.service.exception.AccountHasNoEnoughFundsException;
import br.com.beyond.service.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class MoneyTransferImpl implements MoneyTransfer {

    private final AccountService accountService;

    @Autowired
    public MoneyTransferImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean transfer(int origemAccountId, int destinyAccountId, BigDecimal amount) {
        Account origemAccount = getAccountById(origemAccountId);
        Account destinyAccount = getAccountById(destinyAccountId);

        if ( hasFounds(origemAccount, amount) ) {
            origemAccount.withdrawMoney(amount);
            destinyAccount.depositMoney(amount);

            accountService.update(origemAccount);
            accountService.update(destinyAccount);
            return true;
        } else {
            throw new AccountHasNoEnoughFundsException("Origen account has no enough funds.");
        }

    }

    @Override
    public boolean hasFounds(Account account, BigDecimal amount) {
        return account.hasFunds(amount);
    }

    @Override
    public Account getAccountById(int accountId) {
        return accountService.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

}
