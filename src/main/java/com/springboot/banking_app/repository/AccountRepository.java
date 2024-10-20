package com.springboot.banking_app.repository;

import com.springboot.banking_app.entity.Account;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Id> {

    //Assigning JPA Repository Here ==>
    Account findById(Long id);

    public void deleteById(Long id);

    boolean existsById(Long id);
}
