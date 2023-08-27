package com.tuum.account.controller;

import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.CreateTransactionResponseDto;
import com.tuum.account.service.TransactionManagementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionManagementService transactionManagementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create transaction")
    public CreateTransactionResponseDto createTransaction(@RequestBody @Valid CreateTransactionRequest createTransactionRequest) {
        return transactionManagementService.createTransaction(createTransactionRequest);
    }

}
