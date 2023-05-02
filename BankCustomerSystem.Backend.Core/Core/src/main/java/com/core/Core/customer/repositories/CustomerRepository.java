package com.core.Core.customer.repositories;

import com.core.Core.customer.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    public Customer findByEmail(String email);
    public Customer findByPhoneNumber(String phoneNumber);
}