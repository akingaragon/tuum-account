package com.tuum.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.CreateTransactionResponseDto;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import com.tuum.account.service.TransactionManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;
import static com.tuum.account.util.IntegrationTestHelper.TRANSACTION_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionManagementService transactionManagementService;

    @Test
    void createTransaction() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, Currency.EUR, TransactionDirection.IN, "Description");
        TransactionDto transactionDto = new TransactionDto(TRANSACTION_ID, ACCOUNT_ID, BigDecimal.TEN, Currency.EUR, TransactionDirection.IN, "Description");

        CreateTransactionResponseDto responseDto = new CreateTransactionResponseDto(transactionDto, BigDecimal.TEN);

        when(transactionManagementService.createTransaction(any(CreateTransactionRequest.class))).thenReturn(responseDto);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionDto.transactionId").value(transactionDto.transactionId()))
                .andExpect(jsonPath("$.transactionDto.accountId").value(transactionDto.accountId()))
                .andExpect(jsonPath("$.transactionDto.amount").value(transactionDto.amount()))
                .andExpect(jsonPath("$.transactionDto.currency").value(transactionDto.currency().toString()))
                .andExpect(jsonPath("$.transactionDto.transactionDirection").value(transactionDto.transactionDirection().toString()))
                .andExpect(jsonPath("$.transactionDto.description").value(transactionDto.description()))
                .andExpect(jsonPath("$.balanceAfterTransaction").value(BigDecimal.TEN));

    }

    @Test
    void createTransactionAmountValidationException() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest(ACCOUNT_ID, new BigDecimal(-1), Currency.EUR, TransactionDirection.IN, "Description");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createTransactionDescriptionValidationException() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, Currency.EUR, TransactionDirection.IN, "");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createTransactionCurrencyValidationException() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, null, TransactionDirection.IN, "description");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
}