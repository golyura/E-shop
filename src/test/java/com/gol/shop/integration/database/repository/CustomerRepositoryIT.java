package com.gol.shop.integration.database.repository;

import com.gol.shop.database.entity.Customer;
import com.gol.shop.database.entity.Role;
import com.gol.shop.database.repository.CustomerRepository;
import com.gol.shop.dto.CustomerFilter;
import com.gol.shop.dto.PersonalInfo2;
import com.gol.shop.integration.IntegrationTestBase;
import com.gol.shop.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revisions;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class CustomerRepositoryIT extends IntegrationTestBase {

    private final CustomerRepository customerRepository;

    @Test
    void updateRoles() {
        var ivan = customerRepository.findById(1L);

        assertTrue(ivan.isPresent());

        ivan.ifPresent(customer -> {
            customer.setBirthDate(LocalDate.now());
            assertSame(Role.ADMIN, customer.getRole());
        });

        var updateRoles = customerRepository.updateRoles(Role.CUSTOMER, 1L, 5L);
        assertThat(updateRoles).isEqualTo(2);

//        ivan.get().getCustomerChats().get(0);

        var theSameIvan = customerRepository.findById(1L);

        assertTrue(theSameIvan.isPresent());

        theSameIvan.ifPresent(customer -> {
            assertSame(Role.CUSTOMER, customer.getRole());
            assertEquals(LocalDate.now(), customer.getBirthDate());
        });

    }

    @Test
    void checkQueries() {
        var customers = customerRepository.findAllBy("a", "e");
        assertThat(customers).hasSize(2);

    }

    @Test
    void findFirstById() {
        var customerOptional = customerRepository.findFirstByOrderByIdDesc();
        assertTrue(customerOptional.isPresent());
        customerOptional.ifPresent(customer -> assertEquals(5L, customer.getId()));

    }

    @Test
    void findFirst3ByBirthDate() {
        List<Customer> customers = customerRepository.findFirst3ByBirthDateBeforeOrderByBirthDate(LocalDate.now());
        assertFalse(customers.isEmpty());
        assertThat(customers).hasSize(3);
    }

    @Test
    void checkSort() {
        var sortById = Sort.by("id").descending();
        var sortByIdAndEmail = Sort.by("id").and(Sort.by("email")).descending();

        var customerTypedSort = Sort.sort(Customer.class);
        var sort = customerTypedSort.by(Customer::getFirstname).and(customerTypedSort.by(Customer::getEmail));

        var before = customerRepository.findFirst3ByBirthDateBefore(LocalDate.now(), sortById);
        assertThat(before).hasSize(3);

    }

    @Test
    void checkPageable() {
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by("id"));
        var allBy = customerRepository.findAllBy(pageRequest);
        assertThat(allBy).hasSize(2);
    }

    //Slice, Page

    @Test
    void checkSlice() {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("id"));
        var page = customerRepository.findAllBy(pageRequest);
        assertThat(page).hasSize(2);

//        page.forEach(System.out::println);
        page.forEach(customer -> {
            System.out.println(customer.getCustomerChats().size());
        });

        while (page.hasNext()) {
            page = customerRepository.findAllBy(page.nextPageable());
//             page.forEach(System.out::println);
            page.forEach(customer -> {
                System.out.println(customer.getCustomerChats().size());
            });
        }
    }

    @Test
    void checkProjection() {
        var personalInfos = customerRepository.findAllByBirthDateBefore(LocalDate.now(), PersonalInfo2.class);
        assertThat(personalInfos).hasSize(5);
    }

    @Test
    void checkProjectionNativeQuery() {
        var personalInfos = customerRepository.findAllByBirthDateBefore(LocalDate.now());
        assertThat(personalInfos).hasSize(5);
    }


    @Test
    void checkAuditing() {
        var ivan = customerRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        customerRepository.flush();
        System.out.println();
    }


    @Test
//    @Commit
    void checkEnvers() {
        var ivan = customerRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        customerRepository.flush();
        System.out.println();
    }

    @Test
    @Disabled
    void checkCustomRepositoryImpl(){
        var filter = new CustomerFilter("%a%", LocalDate.now());
        var customers = customerRepository.findAllByFilter(filter);

        assertThat(customers).hasSize(4);

    }

@Test
    void checkQuerydsl(){
    var customers = customerRepository.findAllByFilter(
            new CustomerFilter("a", LocalDate.now())
    );
    assertThat(customers).hasSize(4);
}



}
