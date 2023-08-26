package com.tuum.account.controller;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account")
    public void createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        accountService.createAccount(createAccountRequest);
    }
}
