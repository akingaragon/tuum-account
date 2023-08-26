package com.tuum.account.mapper;

import com.tuum.account.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account (customer_id,country,status) VALUES (#{customerId},#{country},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertAccount(Account account);
}
