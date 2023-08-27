package com.tuum.account.entity;

import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Transaction extends AbstractAuditable implements Serializable {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;
}
