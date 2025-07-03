/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.utility;

import com.jackson.springboot_api_hateoas.constants.LinkRelation;
import com.jackson.springboot_api_hateoas.controller.CustomerController;
import com.jackson.springboot_api_hateoas.dto.CustomerSummaryDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * CustomerSummaryDtoAssembler Class.
 * <p>
 * </p>
 *
 * @author
 */
@Component
public class CustomerSummaryDtoAssembler implements RepresentationModelAssembler<CustomerSummaryDto, EntityModel<CustomerSummaryDto>> {

    @Override
    public EntityModel<CustomerSummaryDto> toModel(CustomerSummaryDto summaryDto) {
        return toModel(summaryDto, Collections.singleton(LinkRelation.DEFAULT));
    }

    public EntityModel<CustomerSummaryDto> toModel(CustomerSummaryDto summaryDto, Set<LinkRelation> linkRelations) {
        EntityModel<CustomerSummaryDto> model = EntityModel.of(summaryDto);

        model.add(
                linkTo(methodOn(CustomerController.class).getAllCustomers(null, null))
                        .withRel(linkRelations.contains(LinkRelation.SELF_GET_ALL) ? "self" : "view-all-customers")
                        .withType("GET"));

        model.add(
                linkTo(methodOn(CustomerController.class).getCustomerById(summaryDto.customerId()))
                        .withRel(linkRelations.contains(LinkRelation.SELF_GET_BY_ID) ? "self" : "view-customer-id")
                        .withType("GET")
                        .withTitle("GET customer by customer Id")
        );
        model.add(
            linkTo(
                    methodOn(CustomerController.class).createCustomer(null)
            )
                    .withRel(linkRelations.contains(LinkRelation.SELF_CREATE) ? "self" : "create-customer")
                    .withType("POST")
                    .withTitle("create new Customer with JSON request fields {customerName, customerAddress} /POST")

        );

        return model;
    }





}
