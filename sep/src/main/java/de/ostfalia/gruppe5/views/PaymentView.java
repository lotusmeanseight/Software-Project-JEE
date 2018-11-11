package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.entity.DataModel;
import de.ostfalia.gruppe5.business.entity.Payment;
import de.ostfalia.gruppe5.business.boundary.PaymentService;

@Named
@RequestScoped
public class PaymentView {

	private Payment payment;
	@Inject
	private PaymentService service;
	private int rowsOnPage;
	private int allRowsCount = 0;

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

}
