package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.entity.DataModel;
import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.business.boundary.OrderService;

@Named
@RequestScoped
public class OrderView {

	private Order order;

	@Inject
	private OrderService service;

	private DataModel orderDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

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

}
