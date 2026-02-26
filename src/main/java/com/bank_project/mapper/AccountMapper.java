package com.bank_project.mapper;

import com.bank_project.vo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper {

        int countUserAccount(@Param("user_id") int user_id);

        List<Account> getAccountsOnDashboard(@Param("user_id") int user_id, @Param("offset") int offset,
                        @Param("count") int count);

        void insertAccount(Account account);

        Account findAccountByID(@Param("account_id") int account_id);

        void updateBalance(@Param("account") Account account);

        Account findForUpdate(@Param("account_id") int account_id);

        int findIDByNumber(@Param("account_number") String account_number);

        Account findAccountByNumber(String account_number);

        Account findForUpdateByNumber(@Param("account_number") String account_number);
}
