package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Customer;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class CustomerService extends AbstractLazyJPAService<Customer> {

	public CustomerService() {
		setEntityClass(Customer.class);

	}

}