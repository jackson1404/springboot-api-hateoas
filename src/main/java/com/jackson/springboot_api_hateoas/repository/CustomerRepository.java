/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.springboot_api_hateoas.repository;

import com.jackson.springboot_api_hateoas.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository Class.
 * <p>
 * </p>
 *
 * @author
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
