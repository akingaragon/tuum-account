package com.tuum.account.entity;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private Long accountId;
    private Long customerId;
    private List<String> balances;
}
