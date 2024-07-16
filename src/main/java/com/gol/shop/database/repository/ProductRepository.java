package com.gol.shop.database.repository;

import com.gol.shop.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Optional, Entity, Future

//    @Query(name = "Product.findByName")

    @Query("select p from Product p " +
            " join fetch p.locales " +
            " where p.name = :name2")

    Optional<Product> findByName(@Param("name2") String name);

    // Collection, Stream (batch, close)
    List<Product> findAllByNameContainingIgnoreCase(String fragment);
}
