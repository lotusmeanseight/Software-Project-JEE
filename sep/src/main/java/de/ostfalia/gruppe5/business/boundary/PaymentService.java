package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Payment;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService extends AbstractLazyJPAService<Payment> {

	public PaymentService() {
		settClass(Payment.class);
	}

}
