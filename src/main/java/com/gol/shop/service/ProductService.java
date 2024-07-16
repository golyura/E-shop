package com.gol.shop.service;

import com.gol.shop.database.repository.ProductRepository;
import com.gol.shop.dto.ProductReadDto;
import com.gol.shop.listener.entity.AccessType;
import com.gol.shop.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new ProductReadDto(entity.getId());
                });
    }
}
