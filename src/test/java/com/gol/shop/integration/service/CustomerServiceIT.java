package com.gol.shop.integration.service;

import com.gol.shop.config.DatabaseProperties;
import com.gol.shop.dto.CustomerReadDto;
import com.gol.shop.integration.IntegrationTestBase;
import com.gol.shop.integration.annotation.IT;
import com.gol.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CustomerServiceIT extends IntegrationTestBase {

    private static final Long CUSTOMER_ID = 1L;

    private final CustomerService userService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        var actualResult = userService.findById(CUSTOMER_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CustomerReadDto(CUSTOMER_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }


}
