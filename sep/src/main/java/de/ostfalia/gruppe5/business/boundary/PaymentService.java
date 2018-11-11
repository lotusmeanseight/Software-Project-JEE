package de.ostfalia.gruppe5.business.boundary;

import java.util.TreeSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Payment;
import de.ostfalia.gruppe5.views.comparators.PaymentComparator;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService extends AbstractLazyJPAService<Payment> {

	public PaymentService() {
		settClass(Payment.class);
	}

}
