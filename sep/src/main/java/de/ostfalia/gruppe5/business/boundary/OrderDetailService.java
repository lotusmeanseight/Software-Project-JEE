package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.OrderDetail;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OrderDetailService extends AbstractJPAService<OrderDetail> {

    public OrderDetailService(){
        settClass(OrderDetail.class);
    }

    @RolesAllowed({"CUSTOMER", "EMPLOYEE"})
    public List<OrderDetail> getAllOrderDetails(Integer orderNumber) {
        TypedQuery<OrderDetail> query = getEntityManager().createQuery("Select o from OrderDetail o " +
                "where o.orderNumber.orderNumber = :orderNumber", OrderDetail.class);
        query.setParameter("orderNumber", orderNumber);
        return query.getResultList();
    }
}