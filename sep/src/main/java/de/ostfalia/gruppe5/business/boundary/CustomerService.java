package de.ostfalia.gruppe5.business.boundary;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Customer;

@RolesAllowed("EMPLOYEE")
@Stateless
public class CustomerService extends AbstractLazyJPAService<Customer> {

	public CustomerService() {
		settClass(Customer.class);

	}

}