package com.tuum.account.mapper;

import com.tuum.account.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO accounts (customer_id) VALUES (#{customerId})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId")
    void insertAccount(Account account);

    @Select("SELECT * FROM accounts WHERE account_id = #{accountId}")
    @Results({
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "balances", javaType = List.class, column = "account_id",
            many = @Many(select = "getBalancesByAccountId"))
    })
    Account getAccountById(Long accountId);

    //@Select("SELECT * FROM balances WHERE account_id = #{accountId}")
    //@Results({
    //    @Result(property = "availableAmount", column = "available_amount"),
    //    @Result(property = "currency", column = "currency")
    //})
    //List<Balance> getBalancesByAccountId(Long accountId);

    // Add other methods as needed
}
