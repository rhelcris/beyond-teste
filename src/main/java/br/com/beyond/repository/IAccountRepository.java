package br.com.beyond.repository;

import br.com.beyond.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> getAccountById(int id);

}
