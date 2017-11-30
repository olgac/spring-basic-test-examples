package com.olgac.examples.service;

import com.olgac.examples.model.dto.CustomerDto;
import com.olgac.examples.model.entitiy.Customer;
import com.olgac.examples.model.request.CreateCustomerRequest;
import com.olgac.examples.model.response.CustomerListResponse;
import com.olgac.examples.repository.CustomerRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerService.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void shouldSave() {
        //freeze time
        DateTime now = new DateTime(2017, 12, 14, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest("Jack Black", "+905556667788", "Istanbul");

        Customer newCustomer = new Customer(createCustomerRequest.getName(), createCustomerRequest.getMobile(),createCustomerRequest.getAddress());
        Customer returnedCustomer = new Customer(createCustomerRequest.getName(), createCustomerRequest.getMobile(),createCustomerRequest.getAddress());
        returnedCustomer.setId(1L);
        returnedCustomer.setCreatedAt(now.toDate());

        when(customerRepository.save(newCustomer)).thenReturn(returnedCustomer);

        Customer savedCustomer = customerService.save(createCustomerRequest);

        verify(customerRepository).save(newCustomer);
        assertThat(savedCustomer.getId(), equalTo(1L));
        assertThat(savedCustomer.getName(), equalTo("Jack Black"));
        assertThat(savedCustomer.getMobile(), equalTo("+905556667788"));
        assertThat(savedCustomer.getAddress(), equalTo("Istanbul"));
        assertThat(savedCustomer.getCreatedAt(), equalTo(now.toDate()));

        verifyNoMoreInteractions(customerRepository);

        //release time
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void shouldList() {
        Customer customer1 = new Customer("John Brown", "+905554443322", "Ankara");
        Customer customer2 = new Customer("Jack Black", "+902223334455", "Izmir");
        Customer customer3 = new Customer("Jimmy Red", "+907776665544", "Antalya");

        when(customerRepository.findByDeletedAtNullOrderByCreatedAtDesc()).thenReturn(Arrays.asList(customer1, customer2, customer3));

        CustomerListResponse customerListResponse = customerService.list();

        List<CustomerDto> customerDtoList = customerListResponse.getCustomers();
        assertThat(customerDtoList, hasSize(3));
        assertThat(customerDtoList.get(0).getName(), equalTo("John Brown"));
        assertThat(customerDtoList.get(0).getMobile(), equalTo("+905554443322"));
        assertThat(customerDtoList.get(0).getAddress(), equalTo("Ankara"));
        assertThat(customerDtoList.get(1).getName(), equalTo("Jack Black"));
        assertThat(customerDtoList.get(1).getMobile(), equalTo("+902223334455"));
        assertThat(customerDtoList.get(1).getAddress(), equalTo("Izmir"));
        assertThat(customerDtoList.get(2).getName(), equalTo("Jimmy Red"));
        assertThat(customerDtoList.get(2).getMobile(), equalTo("+907776665544"));
        assertThat(customerDtoList.get(2).getAddress(), equalTo("Antalya"));

        verify(customerRepository).findByDeletedAtNullOrderByCreatedAtDesc();

        verifyNoMoreInteractions(customerRepository);
    }
}