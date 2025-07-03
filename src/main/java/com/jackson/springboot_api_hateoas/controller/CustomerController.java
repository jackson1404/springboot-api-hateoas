/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.controller;

import com.jackson.springboot_api_hateoas.dto.CustomerSummaryDto;
import com.jackson.springboot_api_hateoas.entity.CustomerEntity;
import com.jackson.springboot_api_hateoas.service.CustomerService;
import com.jackson.springboot_api_hateoas.utility.CustomerSummaryDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private CustomerSummaryDtoAssembler customerSummaryDtoAssembler;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public EntityModel<CustomerSummaryDto> createCustomer(@RequestBody CustomerEntity customer){

        CustomerEntity customerEntity = customerService.createCustomer(customer);

        CustomerSummaryDto summaryDto = new CustomerSummaryDto(customerEntity.getCustomerId(), customerEntity.getCustomerName());

        EntityModel<CustomerSummaryDto> summaryDtoEntityModel = customerSummaryDtoAssembler.toModel(summaryDto);
        summaryDtoEntityModel.add(linkTo(methodOn(CustomerController.class).createCustomer(null))
                                          .withSelfRel()
                                          .withType("POST")
                                          .withTitle("create new Customer with JSON request fields {customerName, customerAddress} /POST"));
        summaryDtoEntityModel.add(linkTo( methodOn(CustomerController.class).getCustomerById(summaryDto.customerId()) )
                                          .withRel("view-details")
                                          .withType("GET"));

        return summaryDtoEntityModel;
    }


    @GetMapping("/getAllCustomers")
    public ResponseEntity<PagedModel<EntityModel<CustomerSummaryDto>>> getAllCustomers(
            @PageableDefault(size = 5) Pageable pageable,
            PagedResourcesAssembler<CustomerSummaryDto> assembler) {

        Page<CustomerEntity> page = customerService.getAllCustomers(pageable);

        // Convert Entity Page → DTO Page
        Page<CustomerSummaryDto> summaryPage = page.map(customer ->
                                                                new CustomerSummaryDto(customer.getCustomerId(), customer.getCustomerName())
        );

        PagedModel<EntityModel<CustomerSummaryDto>> pagedModel = assembler.toModel(
                summaryPage,
                summaryDto -> customerSummaryDtoAssembler.toModel(summaryDto, true)
//                summaryDto -> EntityModel.of(summaryDto,
//                                             linkTo(methodOn(CustomerController.class)
//                                                            .getCustomerById(summaryDto.customerId()))
//                                                     .withRel("view-details")
//                )
        );


//        pagedModel.add(
//                linkTo(
//                        methodOn(CustomerController.class).createCustomer(null)
//                )
//                        .withRel("create-customer")
//                        .withType("POST")
//                        .withTitle("create new Customer with JSON request fields {customerName, customerAddress} /POST")
//        );

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<EntityModel<CustomerEntity>>  getCustomerById(@RequestParam Long customerId){

        CustomerEntity customer = customerService.getCustomerById(customerId);
        EntityModel<CustomerEntity> model = EntityModel.of(customer);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerById(customerId))
                          .withRel("self")
                          .withType("GET")
                          .withTitle("GET customer by customer Id"));

        model.add(linkTo(methodOn(CustomerController.class).getAllCustomers(null, null)).withRel("customer-list"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
    }





}
