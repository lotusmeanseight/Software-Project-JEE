package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Payment;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService {


    @PersistenceContext(name = "simple")
    EntityManager entityManager;

    public PaymentService() {

    }

    public void save(Payment payment) {
        entityManager.persist(payment);
    }

    private Payment findById(String id) {
        return entityManager.find(Payment.class, id);
    }

    public void deleteById(String id) {
        entityManager.remove(findById(id));
    }

    public List<Payment> getAllPayments() {
        return entityManager.createQuery("select p from Payment p", Payment.class).getResultList();
    }

    public Payment update(Payment payment) {
        return entityManager.merge(payment);
    }
}
