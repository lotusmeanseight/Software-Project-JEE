package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.OrderDetail;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OrderDetailService {
    @PersistenceContext(name = "simple")
    EntityManager entityManager;

    public void save(OrderDetail orderDetail) {
        entityManager.persist(orderDetail);
    }

    public OrderDetail update(OrderDetail orderDetail) {
        return entityManager.merge(orderDetail);
    }

    public List<OrderDetail> getAllOrderDetails(Integer orderNumber) {
        TypedQuery<OrderDetail> query = entityManager.createQuery("Select o from OrderDetail o " +
                "where o.orderNumber.orderNumber = :orderNumber", OrderDetail.class);
        query.setParameter("orderNumber", orderNumber);
        return query.getResultList();
    }
}