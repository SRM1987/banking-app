package com.springboot.banking_app.repository;

import com.springboot.banking_app.entity.Account;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Id> {
    Account findById(Long id);
    //Assigning JPA Repository Here ==>
}
