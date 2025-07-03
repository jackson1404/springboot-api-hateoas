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
    public ResponseEntity<PagedModel<EntityModel<CustomerSummaryDto>>> getAllCustomers(
            @PageableDefault(size = 10, sort = "customerId") Pageable pageable,
            PagedResourcesAssembler<CustomerSummaryDto> assembler) {

        Page<CustomerEntity> page = customerService.getAllCustomers(pageable);

        // Convert Entity Page → DTO Page
        Page<CustomerSummaryDto> summaryPage = page.map(customer ->
                                                                new CustomerSummaryDto(customer.getCustomerId(), customer.getCustomerName())
        );

        PagedModel<EntityModel<CustomerSummaryDto>> pagedModel = assembler.toModel(
                summaryPage,
                summaryDto -> EntityModel.of(summaryDto,
                                             linkTo(methodOn(CustomerController.class)
                                                            .getCustomerById(summaryDto.customerId()))
                                                     .withRel("view-customer")
                )
        );

        return ResponseEntity.ok(pagedModel);
    }


    @GetMapping("/getCustomerById")
    public ResponseEntity<EntityModel<CustomerEntity>>  getCustomerById(@RequestParam Long customerId){

        CustomerEntity customer = customerService.getCustomerById(customerId);
        EntityModel<CustomerEntity> model = EntityModel.of(customer);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerById(customerId)).withSelfRel());
//        model.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customer-list"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
    }



}
