package com.tuum.account.entity;

import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Transaction extends AbstractAuditable {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;
}
