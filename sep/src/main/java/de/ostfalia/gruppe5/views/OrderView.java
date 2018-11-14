package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.entity.Order;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OrderView {

	private Order order;

	@Inject
	private OrderService service;

	@Inject
	private OrderDataTable datatable;

	public OrderView() {
		order = new Order();
	}

	public String save() {
		service.save(order);
		return null;
	}

	public String delete(Order order) {
		service.deleteById(order.getOrderNumber());
		return null;
	}

	public String update(Order order) {
		service.update(order);
		return null;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderDataTable getDatatable() {
		return datatable;
	}

}
