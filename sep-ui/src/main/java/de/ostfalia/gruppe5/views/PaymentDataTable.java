package de.ostfalia.gruppe5.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.entity.Payment;

@ViewScoped
@Named
public class PaymentDataTable extends Datatable<PaymentService, Payment> {

	@Inject
	PaymentService service;

	public PaymentDataTable() {

	}

}
