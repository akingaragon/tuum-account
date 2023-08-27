package com.tuum.account.mapper;

import com.tuum.account.entity.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionMapper {
    @Insert("INSERT INTO transaction (account_id,amount,currency,direction,description) VALUES (#{accountId},#{amount},#{currency},#{direction},#{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTransaction(Transaction transaction);

    @Select("SELECT * FROM transaction WHERE account_id = #{accountId}")
    List<Transaction> getAllByAccountId(Long accountId);
}
