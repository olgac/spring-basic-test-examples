package com.olgac.examples.service;

import com.olgac.examples.model.dto.CustomerDto;
import com.olgac.examples.model.entitiy.Customer;
import com.olgac.examples.model.request.CreateCustomerRequest;
import com.olgac.examples.model.response.CustomerListResponse;
import com.olgac.examples.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer(createCustomerRequest.getName(), createCustomerRequest.getMobile(),createCustomerRequest.getAddress());
        return customerRepository.save(customer);
    }

    public CustomerListResponse list() {
        List<Customer> customerList = customerRepository.findByDeletedAtNullOrderByCreatedAtDesc();

        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerList.forEach(each -> {
            customerDtoList.add(new CustomerDto(each.getId(), each.getName(), each.getMobile(), each.getAddress()));
        });

        return new CustomerListResponse(customerDtoList);
    }
}
