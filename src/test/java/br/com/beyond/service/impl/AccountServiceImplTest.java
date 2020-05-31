package br.com.beyond.service.impl;

import br.com.beyond.domain.Account;
import br.com.beyond.repository.IAccountRepository;
import br.com.beyond.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccountServiceImplTest {

    private AccountService accountService;

    @MockBean
    private IAccountRepository repository;

    @BeforeEach
    public void setUp() {
        this.accountService = new AccountServiceImpl( repository );
    }

    @Test
    @DisplayName("Must return an account by id")
    public void mustReturnAccountById() {
        // Cenário
        int id = 1;
        Account jose = Account.builder().id(1).name("José").amount(BigDecimal.valueOf(1500.0)).build();
        BDDMockito.when( repository.getAccountById(Mockito.anyInt()) ).thenReturn( Optional.of(jose) );

        // Ação
        Optional<Account> returnedAccount = accountService.getAccountById(id);

        // Verificação
        assertThat( returnedAccount.isPresent() ).isTrue();
        assertThat( returnedAccount.get().getId() ).isEqualTo( 1 );
        assertThat( returnedAccount.get().getName() ).isEqualTo( "José" );
        assertThat( returnedAccount.get().getAmount() ).isEqualTo( BigDecimal.valueOf(1500.0) );

        Mockito.verify(repository, times(1)).getAccountById( Mockito.anyInt() );
    }

    @Test
    @DisplayName("Must find a nonexistent account")
    public void mustNotFoundANonexistentAccount() {
        // Cenário
        int id = 10;
        BDDMockito.when( repository.getAccountById(Mockito.anyInt()) ).thenReturn( Optional.empty() );

        // Ação
        Optional<Account> returnedAccount = accountService.getAccountById(id);

        // Verificação

        assertThat( returnedAccount.isPresent() ).isFalse();
        Mockito.verify(repository, times(1)).getAccountById(id);
    }

    @Test
    @DisplayName("Must update account")
    public void mustUpdateAccountTest() {
        // Cenário
        int id = 1;
        Account updatingAccount = Account.builder().id(id).name("José").amount(BigDecimal.valueOf(1500.0)).build();

        Account updatedAccount = Account.builder().id(id).name("José").amount(BigDecimal.valueOf(2000.0)).build();

        BDDMockito.when( repository.save( updatingAccount )).thenReturn( updatedAccount );

        // Ação
        Account account = accountService.update(updatingAccount);

        // Verificação
        Mockito.verify(repository, times(1)).save(updatingAccount);
        assertThat( account.getId() ).isEqualTo( updatedAccount.getId() );
        assertThat( account.getName() ).isEqualTo( updatedAccount.getName() );
        assertThat( account.getAmount() ).isEqualTo( updatedAccount.getAmount() );
    }


}
