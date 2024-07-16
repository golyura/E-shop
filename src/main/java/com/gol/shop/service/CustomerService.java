package com.gol.shop.service;

import com.gol.shop.database.repository.CustomerRepository;
import com.gol.shop.dto.CustomerReadDto;
import com.gol.shop.listener.entity.AccessType;
import com.gol.shop.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Optional<CustomerReadDto> findById(Long id) {
        return customerRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CustomerReadDto(entity.getId());
                });
    }
}
