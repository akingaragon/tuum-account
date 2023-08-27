package com.tuum.account.entity;

import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@Accessors(chain = true)
@Builder
public class Account extends AbstractAuditable {

    private Long id;

    private Long customerId;

    private Country country;

    private AccountStatus status;

}
