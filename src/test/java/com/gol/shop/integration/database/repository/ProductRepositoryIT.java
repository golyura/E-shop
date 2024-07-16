package com.gol.shop.integration.database.repository;

import com.gol.shop.database.entity.Product;
import com.gol.shop.database.repository.ProductRepository;
import com.gol.shop.integration.IntegrationTestBase;
import com.gol.shop.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor
//@Commit
class ProductRepositoryIT extends IntegrationTestBase {

    private static final Long APPLE_ID = 5L;
    private final EntityManager entityManager;
    private final ProductRepository productRepository;

    @Test
    void checkQueries() {
        productRepository.findByName("Milk");
        productRepository.findAllByNameContainingIgnoreCase("a");

    }


    @Test
    @Disabled
    void delete() {
        var maybeProduct = productRepository.findById(APPLE_ID);
        assertTrue(maybeProduct.isPresent());

        maybeProduct.ifPresent(productRepository::delete);
        entityManager.flush();
        assertTrue(productRepository.findById(APPLE_ID).isEmpty());

    }


    @Test
    void findById() {
        var product = entityManager.find(Product.class, 1);
        assertNotNull(product);
        assertThat(product.getLocales()).hasSize(2);
    }

    @Test
    void save() {
        var company = Product.builder()
                .name("Cheese")
                .price(3)
                .quantity(1000)
                .locales(Map.of(
                        "ru", "Сыр",
                        "en", "Cheese"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}