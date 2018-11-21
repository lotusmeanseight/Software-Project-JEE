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
		setEntityClass(Order.class);
	}

	@RolesAllowed("CUSTOMER")
	public List<Order> getOrdersByCustomerId(Integer id) {
		return getEntityManager().createQuery("select o from Order o where o.customerNumber = " + id, Order.class)
				.getResultList();
	}

	public Integer nextID(){
		Integer lastID = this.getEntityManager().createQuery("select MAX(o.orderNumber) from Order o", Integer.class).getSingleResult();
		lastID++;

		return lastID;
	}

}
