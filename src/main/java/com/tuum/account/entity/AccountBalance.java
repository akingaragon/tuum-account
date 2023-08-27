package com.tuum.account.entity;

import com.tuum.account.enums.Currency;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AccountBalance extends AbstractAuditable implements Serializable {

    private Long id;

    private Long accountId;

    private BigDecimal availableAmount;

    private Currency currency;

    public AccountBalance(Long accountId, Currency currency) {
        this.accountId = accountId;
        this.currency = currency;
        this.availableAmount = BigDecimal.ZERO;
    }
}
