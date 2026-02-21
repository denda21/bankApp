package com.bank_project.service;

import com.bank_project.mapper.HistoryMapper;
import com.bank_project.vo.Account;
import com.bank_project.helpers.GenAccountNumber;
import com.bank_project.vo.dto.PagedResult;
import com.bank_project.vo.dto.PagingDTO;
import com.bank_project.vo.TransferHistory;
import com.bank_project.mapper.AccountMapper;
import com.bank_project.mapper.TransferMapper;
import com.bank_project.error.InvalidAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransferMapper transferMapper;
    @Autowired
    private HistoryMapper historyMapper;

    @Transactional(readOnly = true)
    public PagedResult<Account> getUserAccounts(int user_id, int request_page) {

        int totalAccounts = accountMapper.countUserAccount(user_id);
        PagingDTO pagingDTO = new PagingDTO(totalAccounts, request_page);

        List<Account> user_accounts = accountMapper.getAccountsOnDashboard(
                user_id, pagingDTO.startIndex(), pagingDTO.getRowPerPage());

        return new PagedResult<>(user_accounts, pagingDTO);
    }

    @Transactional
    public void createAccount(int user_id, String account_name) {

        String account_number = GenAccountNumber.generateAccountNumber();
        Account account = new Account();
        account.setAccount_name(account_name);
        account.setUser_id(user_id);
        account.setAccount_number(account_number);
        account.setBalance(BigDecimal.ZERO);
        account.setUpdated_at(LocalDateTime.now());

        accountMapper.insertAccount(account);
    }

    @Transactional(readOnly = true)
    public PagedResult<TransferHistory> getAccountHistory(String account_number, int page) {

        int account_id = accountMapper.findIDByNumber(account_number);
        int history_count = historyMapper.countAccountHistory(account_id);
        PagingDTO pagingDTO = new PagingDTO(history_count, page);

        List<TransferHistory> history_list = historyMapper.findHistoryByAccountId(
                account_id, pagingDTO.startIndex(), pagingDTO.getRowPerPage());

        return new PagedResult<>(history_list, pagingDTO);
    }



    //testing methods

    @Transactional
    public Account createAccountWithBalance(int user_id, String account_name, BigDecimal initialBalance) {

        String account_number = GenAccountNumber.generateAccountNumber();

        Account account = new Account();
        account.setUser_id(user_id);
        account.setAccount_name(account_name);
        account.setAccount_number(account_number);
        account.setBalance(initialBalance);
        account.setUpdated_at(LocalDateTime.now());

        accountMapper.insertAccount(account);
        return account;
    }

    public Account findAccountByNumber(String account_number) {
        return accountMapper.findAccountByNumber(account_number);
    }

    public Account findAccountByID(int account_id) {
        return accountMapper.findAccountByID(account_id);
    }

}
