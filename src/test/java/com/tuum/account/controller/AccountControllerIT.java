package com.tuum.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import com.tuum.account.service.AccountManagementService;
import com.tuum.account.service.TransactionManagementService;
import com.tuum.account.util.IntegrationTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.tuum.account.util.IntegrationTestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountManagementService accountManagementService;

    @MockBean
    private TransactionManagementService transactionManagementService;

    @Test
    void createAccount() throws Exception {
        CreateAccountRequest request = getCreateAccountRequest();
        AccountDto accountDto = createAccountDto();

        when(accountManagementService.createAccountWithInitialBalances(any(CreateAccountRequest.class))).thenReturn(accountDto);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(accountDto.accountId()))
                .andExpect(jsonPath("$.customerId").value(accountDto.customerId()))
                .andExpect(jsonPath("$.balances[0].availableAmount").value(accountDto.balances().get(0).availableAmount()))
                .andExpect(jsonPath("$.balances[0].currency").value(accountDto.balances().get(0).currency().toString()));
    }

    @Test
    void createAccountEmptyCurrencyListException() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(CUSTOMER_ID, COUNTRY, List.of());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAccountNullCustomerIdException() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(null, COUNTRY, List.of());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getAccountById() throws Exception {
        AccountDto accountDto = createAccountDto();

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);

        mockMvc.perform(get("/account/{id}", ACCOUNT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountDto.accountId()))
                .andExpect(jsonPath("$.customerId").value(accountDto.customerId()))
                .andExpect(jsonPath("$.balances[0].availableAmount").value(accountDto.balances().get(0).availableAmount()))
                .andExpect(jsonPath("$.balances[0].currency").value(accountDto.balances().get(0).currency().toString()));

    }

    @Test
    void getTransactionsByAccountId() throws Exception {
        TransactionDto transactionDto = new TransactionDto(TRANSACTION_ID, ACCOUNT_ID, BigDecimal.TEN, Currency.EUR, TransactionDirection.IN, "Description");

        List<TransactionDto> transactions = Collections.singletonList(transactionDto);

        when(transactionManagementService.getTransactionsByAccountId(IntegrationTestHelper.ACCOUNT_ID)).thenReturn(transactions);

        mockMvc.perform(get("/account/{id}/transaction", IntegrationTestHelper.ACCOUNT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(transactionDto.transactionId()))
                .andExpect(jsonPath("$[0].accountId").value(transactionDto.accountId()))
                .andExpect(jsonPath("$[0].amount").value(transactionDto.amount()))
                .andExpect(jsonPath("$[0].currency").value(transactionDto.currency().toString()))
                .andExpect(jsonPath("$[0].transactionDirection").value(transactionDto.transactionDirection().toString()))
                .andExpect(jsonPath("$[0].description").value(transactionDto.description()));

    }
}