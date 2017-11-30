package com.olgac.examples.controller;

import com.olgac.examples.model.request.CreateCustomerRequest;
import com.olgac.examples.model.response.CustomerListResponse;
import com.olgac.examples.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        customerService.save(createCustomerRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<CustomerListResponse> listCustomers() {
        CustomerListResponse listCustomersResponse = customerService.list();
        return new ResponseEntity<>(listCustomersResponse, HttpStatus.OK);
    }

}
