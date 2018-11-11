package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.Payment;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService extends AbstractLazyJPAService<Payment> {

	public PaymentService() {
		settClass(Payment.class);
	}

}
