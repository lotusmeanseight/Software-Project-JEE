package de.ostfalia.gruppe5.business.boundary;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.views.comparators.OrderComparator;

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
