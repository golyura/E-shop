package com.gol.shop.database.repository;

import com.gol.shop.database.entity.Customer;
import com.gol.shop.dto.CustomerFilter;

import java.util.List;

public interface FilterCustomerRepository {
    List<Customer> findAllByFilter(CustomerFilter filter);
}
