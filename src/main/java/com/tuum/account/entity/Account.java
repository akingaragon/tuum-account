package com.tuum.account.entity;

import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Account extends AbstractAuditable {

    private Long id;

    private Long customerId;

    private Country country;

    private AccountStatus status;

    public Account(Long customerId, Country country, AccountStatus status) {
        this.customerId = customerId;
        this.country = country;
        this.status = status;
    }
}
