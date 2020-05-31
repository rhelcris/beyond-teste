package br.com.beyond.service.impl;

import br.com.beyond.domain.Account;
import br.com.beyond.service.AccountService;
import br.com.beyond.service.MoneyTransfer;
import br.com.beyond.service.exception.AccountHasNoEnoughFundsException;
import br.com.beyond.service.exception.AccountNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MoneyTransferImplTest {

    MoneyTransfer moneyTransfer;

    @MockBean
    AccountService accountService;

    @BeforeEach
    public void setUp() {
        this.moneyTransfer = new MoneyTransferImpl(accountService);
    }

    @Test
    @DisplayName("Must verify origen Account has founds")
    public void mustVerifyAccountHasFounds() {
        // Cenário
        Account account = Account.builder().id(1).name("José").amount(BigDecimal.valueOf(1500.0)).build();

        // Ação
        boolean hasFounds = moneyTransfer.hasFounds(account, BigDecimal.valueOf(500.0));

        // Verificação
        assertThat( hasFounds ).isTrue();
    }

    @Test
    @DisplayName("Checar se conta origem possui saldo")
    public void checkOriginAccountHasFounds() {
        // Cenário
        Account account = Account.builder().id(1).name("José").amount(BigDecimal.valueOf(1500.0)).build();
        BigDecimal valueToTransfer = BigDecimal.valueOf(500.0);

        // Ação
        boolean hasFunds = account.hasFunds(valueToTransfer);

        // Verificação
        assertThat( hasFunds ).isTrue();
    }

    @Test
    @DisplayName("Deve constatar que conta não possui saldo")
    public void mustVerifyAccountWithoutFunds() {
        // Cenário
        Account account = Account.builder().id(1).name("José").amount(BigDecimal.valueOf(1500.0)).build();
        BigDecimal valueToTransfer = BigDecimal.valueOf(1500.01);

        // Ação
        boolean hasFunds = account.hasFunds(valueToTransfer);

        // Verificação
        assertThat( hasFunds ).isFalse();
    }


    @Test
    @DisplayName("Deve veficar se conta possui saldo passando o valor exato do saldo")
    public void mustVerifyAccountWithExactFunds() {
        // Cenário
        Account account = Account.builder().id(1).name("José").amount(BigDecimal.valueOf(1500.0)).build();
        BigDecimal valueToTransfer = BigDecimal.valueOf(1500.0);

        // Ação
        boolean hasFunds = account.hasFunds(valueToTransfer);

        // Verificação
        assertThat( hasFunds ).isTrue();
    }

    @Test
    @DisplayName("Deve falhar se conta de origem não existir")
    public void mustFailWhenNonexistentOriginAccount() {
        // Cenário
        int originAccountId = 1;

        // Ação
        Throwable exception = Assertions.catchThrowable(() -> moneyTransfer.getAccountById(originAccountId));

        // Verificação
        assertThat( exception )
                .isInstanceOf( AccountNotFoundException.class )
                .hasMessage( "Account not found" );
    }

    @Test
    @DisplayName("Deve falhar se conta de destino não existir")
    public void mustFailWhenNonexistentDestinyAccount() {
        // Cenário
        int originAccountId = 10;

        // Ação
        Throwable exception = Assertions.catchThrowable(() -> moneyTransfer.getAccountById(originAccountId));

        // Verificação
        assertThat( exception )
                .isInstanceOf( AccountNotFoundException.class )
                .hasMessage( "Account not found" );
    }

    @Test
    @DisplayName("Deve transferir dinheiro entre as contas")
    public void mustTransferMoneyBetweenAccounts() {
        // Cenário
        int originAccountId = 1;
        int destinyAccountId = 2;
        BigDecimal fundsToTransfer = new BigDecimal(300.0);

        Account originAccount = Account.builder().id(originAccountId).name("José").amount(BigDecimal.valueOf(1500.0)).build();
        Account destinyAccount = Account.builder().id(destinyAccountId).name("Maria").amount(BigDecimal.valueOf(500.0)).build();

        BDDMockito.when( accountService.getAccountById(originAccountId) ).thenReturn( Optional.of(originAccount) );
        BDDMockito.when( accountService.getAccountById(destinyAccountId) ).thenReturn( Optional.of(destinyAccount) );

        // Ação
        boolean fundsTransferred = moneyTransfer.transfer(originAccountId, destinyAccountId, fundsToTransfer);

        // Verificação
        assertThat( fundsTransferred ).isTrue();
        assertThat( originAccount.getAmount() ).isEqualTo( BigDecimal.valueOf(1200.0) );
        assertThat( destinyAccount.getAmount() ).isEqualTo( BigDecimal.valueOf(800.0) );
    }


    @Test
    @DisplayName("Deve falhar se não tiver dinheiro suficiente na conta de origem")
    public void mustFailWhenOriginAccountNoHasEnoughFunds() {
        // Cenário
        int originAccountId = 1;
        int destinyAccountId = 2;
        BigDecimal fundsToTransfer = new BigDecimal(300.0);

        Account originAccount = Account.builder().id(originAccountId).name("José").amount(BigDecimal.valueOf(250)).build();
        Account destinyAccount = Account.builder().id(destinyAccountId).name("Maria").amount(BigDecimal.valueOf(500.0)).build();

        BDDMockito.when( accountService.getAccountById(originAccountId) ).thenReturn( Optional.of(originAccount) );
        BDDMockito.when( accountService.getAccountById(destinyAccountId) ).thenReturn( Optional.of(destinyAccount) );

        // Ação
        Throwable exception = Assertions.catchThrowable(() -> moneyTransfer.transfer(originAccountId, destinyAccountId, fundsToTransfer));

        // Verificação
        assertThat( exception )
                .isInstanceOf( AccountHasNoEnoughFundsException.class )
                .hasMessage( "Origen account has no enough funds." );
    }

}

