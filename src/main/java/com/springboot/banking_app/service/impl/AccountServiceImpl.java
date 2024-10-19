package com.springboot.banking_app.service.impl;

import com.springboot.banking_app.dto.AccountDto;
import com.springboot.banking_app.entity.Account;
import com.springboot.banking_app.mapper.AccountMapper;
import com.springboot.banking_app.repository.AccountRepository;
import com.springboot.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        try {
            Account account = accountRepository.findById(id);
            return AccountMapper.mapToAccountDto(account);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDto depositAmount(Long id, double amount) {
        try {
            Account account = accountRepository.findById(id);
           double total =  account.getBalance() + amount;
           account.setBalance(total);
           Account savedAccount = accountRepository.save(account);
            return AccountMapper.mapToAccountDto(savedAccount);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

    }
}
