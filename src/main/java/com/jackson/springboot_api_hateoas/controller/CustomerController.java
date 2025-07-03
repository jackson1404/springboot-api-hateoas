/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.controller;

import com.jackson.springboot_api_hateoas.entity.CustomerEntity;
import com.jackson.springboot_api_hateoas.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * ApiController Class.
 * <p>
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAllCustomers")
    public List<CustomerEntity> getAllCustomers(){

        return customerService.getAllCustomers();
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<EntityModel<CustomerEntity>>  getCustomerById(@RequestParam Long customerId){

        CustomerEntity customer = customerService.getCustomerById(customerId);
        EntityModel<CustomerEntity> model = EntityModel.of(customer);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerById(customerId)).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customer-list"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
    }



}
