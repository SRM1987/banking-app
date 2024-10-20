package com.springboot.banking_app.service;

import com.springboot.banking_app.dto.AccountDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto depositAmount(Long id, double amount);

    AccountDto withdrawAmount(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);
}
