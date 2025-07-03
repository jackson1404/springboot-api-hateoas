/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.utility;

import com.jackson.springboot_api_hateoas.controller.CustomerController;
import com.jackson.springboot_api_hateoas.dto.CustomerSummaryDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

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
        return toModel(summaryDto, false);
    }

    public EntityModel<CustomerSummaryDto> toModel(CustomerSummaryDto summaryDto, boolean isSelfRef) {
        return EntityModel.of(
                summaryDto,
                linkTo(methodOn(CustomerController.class).getAllCustomers(null, null))
                        .withRel(isSelfRef ? "self" : "view-all-customers")
                        .withType("GET")

        );
    }
}
