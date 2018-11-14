package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.entity.Order;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class OrderDataTable extends Datatable<OrderService, Order> {

	@Inject
	private OrderService service;

	public OrderDataTable() {

	}

}
