package com.tuum.account.entity;

import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class Account extends AbstractAuditable implements Serializable {

    private Long id;

    private Long customerId;

    private Country country;

    private AccountStatus status;

}
