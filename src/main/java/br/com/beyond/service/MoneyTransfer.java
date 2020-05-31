package br.com.beyond.service;

import br.com.beyond.domain.Account;

import java.math.BigDecimal;

/**
 * Transfer money between accounts
 */
public interface MoneyTransfer {

    /**
     * This method has responsibility for transfer money between accounts
     * @param origemAccountId is the origem account
     * @param destinyAccountId  is the destiny account
     * @param amount is the amount to be transfer
     * @return this method return if the transfer happend
     */
    boolean transfer(int origemAccountId, int destinyAccountId, BigDecimal amount );
    boolean hasFounds(Account account, BigDecimal amount );
    Account getAccountById(int accountId);

}
