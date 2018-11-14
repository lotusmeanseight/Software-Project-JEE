package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Order;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed("EMPLOYEE")
@Stateless
public class OrderService extends AbstractLazyJPAService<Order> {
	public OrderService() {
		settClass(Order.class);
	}

	@RolesAllowed("CUSTOMER")
	public List<Order> getOrdersByCustomerId(Integer id) {
		return getEntityManager().createQuery("select o from Order o where o.customerNumber = " + id, Order.class)
				.getResultList();
	}

}
