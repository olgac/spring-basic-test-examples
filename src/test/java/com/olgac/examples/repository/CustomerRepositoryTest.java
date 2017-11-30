package com.olgac.examples.repository;

import com.olgac.examples.model.entitiy.Customer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldFindByDeletedAtNullOrderByCreatedAtDesc() {
        Customer customer1 = new Customer("Jack Black", "+905556667788", "Konya");
        Customer customer2 = new Customer("Joe Black", "+905553334455", "Afyon");
        Customer customer3 = new Customer("John Black", "+905552224455", "Isparta");

        testEntityManager.persist(customer1);
        testEntityManager.persist(customer2);
        testEntityManager.persist(customer3);

        testEntityManager.flush();
        testEntityManager.clear();

        List<Customer> resultList = customerRepository.findByDeletedAtNullOrderByCreatedAtDesc();

        assertThat(resultList, hasSize(3));
        assertThat(resultList.get(0).getId(), notNullValue());
        assertThat(resultList.get(0).getName(), equalTo("John Black"));
        assertThat(resultList.get(0).getMobile(), equalTo("+905552224455"));
        assertThat(resultList.get(0).getAddress(), equalTo("Isparta"));
        assertThat(resultList.get(1).getId(), notNullValue());
        assertThat(resultList.get(1).getName(), equalTo("Joe Black"));
        assertThat(resultList.get(1).getMobile(), equalTo("+905553334455"));
        assertThat(resultList.get(1).getAddress(), equalTo("Afyon"));
        assertThat(resultList.get(2).getId(), notNullValue());
        assertThat(resultList.get(2).getName(), equalTo("Jack Black"));
        assertThat(resultList.get(2).getMobile(), equalTo("+905556667788"));
        assertThat(resultList.get(2).getAddress(), equalTo("Konya"));
    }
}