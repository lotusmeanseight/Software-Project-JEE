package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.OrderDetailService;
import de.ostfalia.gruppe5.business.entity.OrderDetail;

@Named
@RequestScoped
public class OrderDetailView {

	private OrderDetail orderDetail;
	@Inject
	private OrderDetailService service;

	public OrderDetailView() {
		this.orderDetail = new OrderDetail();
	}

	public List<OrderDetail> getOrderDetails(Integer orderNumber) {
		return service.getAllOrderDetails(orderNumber);
	}

	public String save() {
		service.save(orderDetail);
		return null;
	}

	public String update(OrderDetail order) {
		service.update(order);
		return null;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
}
