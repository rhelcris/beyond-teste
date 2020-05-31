package br.com.beyond.repository;

import br.com.beyond.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class IAccountRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IAccountRepository iAccountRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Must return an existenting account by id ")
    public void mustReturnAnExistingAccountByID() {
        // Cenário
        Account jose = Account.builder().name("José").amount(BigDecimal.valueOf(1000.0)).build();
        Account savedAccount = iAccountRepository.save(jose);

        // Ação
        Optional<Account> returnedAccount = iAccountRepository.getAccountById(savedAccount.getId());

        // Verificação
        assertThat( returnedAccount.isPresent() ).isTrue();
    }

    @Test
    @DisplayName("Must save an account")
    public void mustSaveAccountTest() {
        // Cenário
        Account savingAccount = Account.builder().name("José").amount(BigDecimal.valueOf(1500.0)).build();

        // Ação
        Account savedAccount = iAccountRepository.save(savingAccount);

        // Verificação
        assertThat( savedAccount ).isNotNull();
        assertThat( savedAccount.getId() ).isEqualTo( 1 );
        assertThat( savedAccount.getName() ).isEqualTo( savingAccount.getName() );
        assertThat( savedAccount.getAmount() ).isEqualTo( savingAccount.getAmount() );
    }

}
