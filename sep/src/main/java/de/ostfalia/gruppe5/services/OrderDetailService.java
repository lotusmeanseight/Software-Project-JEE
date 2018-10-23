package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.interfaces.OrderDetail;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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