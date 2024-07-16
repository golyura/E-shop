package com.gol.shop.service;

import com.gol.shop.database.entity.Customer;
import com.gol.shop.database.repository.CustomerRepository;
import com.gol.shop.dto.CustomerReadDto;
import com.gol.shop.listener.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Long CUSTOMER_ID = 1L;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void findById() {
        doReturn(Optional.of(new Customer(CUSTOMER_ID, null,null,null,null, Collections.emptyList())))
                .when(customerRepository).findById(CUSTOMER_ID);

        var actualResult = customerService.findById(CUSTOMER_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CustomerReadDto(CUSTOMER_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoMoreInteractions(eventPublisher, customerRepository);
    }
}