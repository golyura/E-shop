package com.gol.shop.database.repository;

import com.gol.shop.database.entity.Customer;
import com.gol.shop.database.entity.Role;
import com.gol.shop.dto.PersonalInfo2;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends
        JpaRepository<Customer, Long>,
        FilterCustomerRepository,
        RevisionRepository<Customer, Long, Integer> {

    @Query("select c from Customer c " +
            "where c.firstname like %:firstname% and c.email like %:email%")
    List<Customer> findAllBy(String firstname, String email);

    @Query(value = "SELECT c.* FROM customer c WHERE c.firstname = :username",
            nativeQuery = true)
    List<Customer> findAllByUsername(String username);

    @Query("update Customer c " +
            " set c.role = :role" +
            " where c.id in (:ids)")
    @Modifying(clearAutomatically = true, flushAutomatically = false)
    int updateRoles(Role role, long... ids);

    Optional<Customer> findFirstByOrderByIdDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = { @QueryHint(name = "org.hibernate.fetchSize", value = "50") })
    List<Customer> findFirst3ByBirthDateBeforeOrderByBirthDate(LocalDate birthDate);

    List<Customer> findFirst3ByBirthDateBefore(LocalDate birthDate, Sort sort);

//    List<Customer> findAllBy(Pageable pageable);

    //    @EntityGraph("Customer.customerChats")
    @EntityGraph(attributePaths = {"customerChats", "customerChats.chat"})
    @Query(value = "select c from Customer c",
            countQuery = "select count(distinct c.firstname) from Customer c")
    Page<Customer> findAllBy(Pageable pageable);

//    List<PersonalInfo> findAllByBirthDateBefore(LocalDate date);

   <T> List<T> findAllByBirthDateBefore(LocalDate date, Class<T> tClass);

    @Query(value = "SELECT firstname, " +
            "email, " +
            "birth_date birthDate " +
            "FROM customer " +
            "WHERE birth_date < :date",
            nativeQuery = true)
        List<PersonalInfo2> findAllByBirthDateBefore(LocalDate date);

}
