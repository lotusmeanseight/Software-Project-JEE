package de.ostfalia.gruppe5.business.boundary;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.Order;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
@Stateless
public class OrderService extends AbstractTableJPAService<Order> {
	public OrderService() {
		setEntityClass(Order.class);
	}

	public List<Order> getOrdersByCustomerId(Integer id) {
		return getEntityManager().createQuery("select o from Order o where o.customerNumber = " + id, Order.class)
				.getResultList();
	}

	public Integer nextID() {
		Integer lastID = this.getEntityManager().createQuery("select MAX(o.orderNumber) from Order o", Integer.class)
				.getSingleResult();

		return ++lastID;
	}

}
