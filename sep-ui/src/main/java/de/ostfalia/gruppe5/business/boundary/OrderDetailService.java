package de.ostfalia.gruppe5.business.boundary;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.OrderDetail;

//@DeclareRoles({ "CUSTOMER", "EMPLOYEE" })
//@RolesAllowed({ "CUSTOMER", "EMPLOYEE" })
@Stateless
public class OrderDetailService extends AbstractJPAService<OrderDetail> {

	public OrderDetailService() {
		setEntityClass(OrderDetail.class);
	}

	//@RolesAllowed({ "CUSTOMER", "EMPLOYEE" })
	public List<OrderDetail> getAllOrderDetails(Integer orderNumber) {
		TypedQuery<OrderDetail> query = getEntityManager().createQuery(
				"Select o from OrderDetail o " + "where o.orderNumber.orderNumber = :orderNumber", OrderDetail.class);
		query.setParameter("orderNumber", orderNumber);
		return query.getResultList();
	}
}