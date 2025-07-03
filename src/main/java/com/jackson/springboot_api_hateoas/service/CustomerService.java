/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.service;

import com.jackson.springboot_api_hateoas.entity.CustomerEntity;
import com.jackson.springboot_api_hateoas.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerService Class.
 * <p>
 * </p>
 *
 * @author
 */

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }


    public CustomerEntity getCustomerById(Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new RuntimeException("Not found"));

        return customer;
    }
}
