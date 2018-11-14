package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.entity.Payment;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PaymentView {

	private Payment payment;
	@Inject
	private PaymentService service;

	@Inject
	private PaymentDataTable datatable;

	public PaymentView() {
		payment = new Payment();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String save() {
		service.save(payment);
		return null;
	}

	public String update(Payment payment) {
		service.update(payment);
		return null;
	}

	public String delete(Payment payment) {
		service.delete(payment);
		return null;
	}

	public PaymentDataTable getDatatable() {
		return datatable;
	}

}
