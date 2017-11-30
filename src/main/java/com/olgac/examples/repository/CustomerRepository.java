package com.olgac.examples.repository;

import com.olgac.examples.model.entitiy.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByDeletedAtNullOrderByCreatedAtDesc();

}