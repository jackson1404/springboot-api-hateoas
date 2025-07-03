/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.entity;

import jakarta.persistence.*;

/**
 * CustomerEntity Class.
 * <p>
 * </p>
 *
 * @author
 */
@Entity
@Table(name = "tbl_customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String customerName;

    private String customerAddress;

    public CustomerEntity(){}

    public CustomerEntity(Long customerId, String customerName, String customerAddress) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
