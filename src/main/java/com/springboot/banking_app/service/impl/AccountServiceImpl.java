package com.springboot.banking_app.service.impl;

import com.springboot.banking_app.dto.AccountDto;
import com.springboot.banking_app.dto.TransferFundDto;
import com.springboot.banking_app.entity.Account;
import com.springboot.banking_app.exception.AccountException;
import com.springboot.banking_app.mapper.AccountMapper;
import com.springboot.banking_app.repository.AccountRepository;
import com.springboot.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (!accountRepository.existsById(id)) {
            throw new AccountException("Account with ID " + id + " not found.");
        }
        Account account = accountRepository.findById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto depositAmount(Long id, double amount) {
        if (!accountRepository.existsById(id)) {
            throw new AccountException("Account with ID " + id + " not found.");
        }
        Account account = accountRepository.findById(id);
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdrawAmount(Long id, double amount) {
        if (!accountRepository.existsById(id)) {
            throw new AccountException("Account with ID " + id + " not found.");
        }
        Account account = accountRepository.findById(id);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Amount.");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountException("Account with ID " + id + " not found.");
        }
        Account account = accountRepository.findById(id);
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundDto transferFundDto) {
        //Retrieve the account from which we send money
        if (!accountRepository.existsById(transferFundDto.fromAccountId())) {
            throw new AccountException("Account with ID " + transferFundDto.fromAccountId() + " not found.");
        }
        Account fromAccount = accountRepository.findById(transferFundDto.fromAccountId());

        //Retrieve the account which we receive the money
        if (!accountRepository.existsById(transferFundDto.toAccountId())) {
            throw new AccountException("Account with ID " + transferFundDto.toAccountId() + " not found.");
        }
        Account toAccount = accountRepository.findById(transferFundDto.toAccountId());

        //Debit the amount from the fromAccount Object
        fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.amount());

        //Credit the amount to the toAccount object
        toAccount.setBalance(toAccount.getBalance() + transferFundDto.amount());

        //Save Both Amounts in Database
        accountRepository.save(fromAccount);

        accountRepository.save(toAccount);
    }
}
