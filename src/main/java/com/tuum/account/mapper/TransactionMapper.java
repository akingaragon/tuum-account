package com.tuum.account.mapper;

import com.tuum.account.entity.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TransactionMapper {
    @Insert("INSERT INTO transaction (account_id,amount,currency,direction,description) VALUES (#{accountId},#{amount},#{currency},#{direction},#{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTransaction(Transaction transaction);
}
