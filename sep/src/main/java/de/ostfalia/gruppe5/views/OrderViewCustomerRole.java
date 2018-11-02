package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Order;
import de.ostfalia.gruppe5.services.OrderService;
import de.ostfalia.gruppe5.userInformation.CustomerUser;

@Named
@RequestScoped
public class OrderViewCustomerRole {

	private Order order;
	
	@Inject
	CustomerUser user;
	
	@Inject
	private OrderService service;

	public List<Order> getOrders() {
		return service.getOrdersByCustomerId(user.getId());
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
