package com.tuum.account.mapper;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance (account_id, available_amount, currency) VALUES (#{accountId}, #{availableAmount}, #{currency})")
    void insertAccountBalance(AccountBalance accountBalance);

    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId}")
    List<AccountBalance> getAccountBalancesByAccountId(Long accountId);

    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId} and currency = #{currency}")
    AccountBalance getAccountBalancesByAccountIdAndCurrency(Long accountId, Currency currency);

    @Update("UPDATE account_balance SET available_amount = #{availableAmount}, updated_by = #{updatedBy}, updated_date = #{updatedDate} WHERE id = #{id}")
    @Result(property = "id", column = "id")
    void updateAccountBalance(AccountBalance accountBalance);
}
