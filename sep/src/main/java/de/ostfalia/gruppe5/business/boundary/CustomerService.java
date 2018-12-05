package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Customer;

@RolesAllowed("EMPLOYEE")
@Stateless
public class CustomerService extends AbstractTableJPAService<Customer> {

	@Override
	public void save(Customer entity) {
		TypedQuery<Integer> customerTypedQuery = getEntityManager()
				.createQuery("select MAX(c.customerNumber) " + "from Customer c", Integer.class);
		entity.setCustomerNumber(customerTypedQuery.getResultList().get(0) + 1);
		super.save(super.update(entity));
	}

	public CustomerService() {
		setEntityClass(Customer.class);

	}

}