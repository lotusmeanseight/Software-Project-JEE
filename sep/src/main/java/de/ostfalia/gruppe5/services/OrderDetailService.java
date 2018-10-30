package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.OrderDetail;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RolesAllowed("internal-user")
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
}