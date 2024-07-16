package com.gol.shop.database.repository;

import com.gol.shop.database.entity.Customer;
import com.gol.shop.database.entity.QCustomer;
import com.gol.shop.database.querydsl.QPredicates;
import com.gol.shop.dto.CustomerFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.gol.shop.database.entity.QCustomer.customer;

@RequiredArgsConstructor
public class FilterCustomerRepositoryImpl implements FilterCustomerRepository {

    private  final EntityManager entityManager;

    @Override
    public List<Customer> findAllByFilter(CustomerFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), customer.firstname::containsIgnoreCase)
                .add(filter.birthDate(), customer.birthDate::before)
                .build();

        return new JPAQuery<Customer>(entityManager)
                .select(customer)
                .from(customer)
                .where(predicate)
                .fetch();
    }

//    @Override
//    public List<Customer> findAllByFilter(CustomerFilter filter) {
//        var cb = entityManager.getCriteriaBuilder();
//        var criteria = cb.createQuery(Customer.class);
//
//        var customer = criteria.from(Customer.class);
//        criteria.select(customer);
//
//        List<Predicate> predicates = new ArrayList<>();
//        if (filter.firstname() != null) {
//            predicates.add(cb.like(customer.get("firstname"), filter.firstname()));
//        }
//                if (filter.birthDate() != null) {
//            predicates.add(cb.lessThan(customer.get("birthDate"), filter.birthDate()));
//        }
//        criteria.where(predicates.toArray(Predicate[]::new));
//
//        return entityManager.createQuery(criteria).getResultList();
//    }

}
