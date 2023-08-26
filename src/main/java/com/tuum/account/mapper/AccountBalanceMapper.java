package com.tuum.account.mapper;

import com.tuum.account.entity.AccountBalance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance (account_id, available_amount, currency) VALUES (#{accountId}, #{availableAmount}, #{currency})")
    void insertAccountBalance(AccountBalance accountBalance);

    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId}")
    List<AccountBalance> getAccountBalancesByAccountId(Long accountId);
}
