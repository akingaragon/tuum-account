package com.tuum.account.dto.response;

import java.util.List;

public record AccountDto(Long accountId, Long customerId, List<AccountBalanceDto> balances) {

}