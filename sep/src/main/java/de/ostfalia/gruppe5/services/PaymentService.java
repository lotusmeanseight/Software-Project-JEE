package de.ostfalia.gruppe5.services;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.models.Payment;
import de.ostfalia.gruppe5.views.comparators.PaymentComparator;

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

	public Payment findById(String id) {
		return entityManager.find(Payment.class, id);
	}

	public void deleteById(String id) {
		entityManager.remove(findById(id));
	}

	public void delete(Payment payment) {
		entityManager.remove(entityManager.merge(payment));
	}

	public List<Payment> getAllPayments() {
		return entityManager.createQuery("select p from Payment p", Payment.class).getResultList();
	}

	public Payment update(Payment payment) {
		return entityManager.merge(payment);
	}

	public TreeSet<Payment> getAllPaymentsLazy(int first, int max) {
		TypedQuery<Payment> query = entityManager.createNamedQuery("Payment.findAll", Payment.class);
		query.setFirstResult(first);
		query.setMaxResults(max);
		TreeSet<Payment> treeSet = new TreeSet<Payment>(new PaymentComparator());
		for (Payment p : query.getResultList()) {
			treeSet.add(p);
		}
		return treeSet;
	}

	public int countPayments() {
		Query query = entityManager.createNamedQuery("Payment.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}

}
