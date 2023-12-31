package com.tuum.account.controller;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.service.AccountManagementService;
import com.tuum.account.service.TransactionManagementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountManagementService accountManagementService;

    private final TransactionManagementService transactionManagementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account")
    public AccountDto createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return accountManagementService.createAccountWithInitialBalances(createAccountRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get account info")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountManagementService.getAccount(id);
    }

    @GetMapping("/{id}/transaction")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get transactions for an account")
    public List<TransactionDto> getTransactionsByAccountId(@PathVariable Long id) {
        return transactionManagementService.getTransactionsByAccountId(id);
    }
}
