package de.ostfalia.gruppe5.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.OrderDetail;
import de.ostfalia.gruppe5.services.OrderDetailService;

@Named
@RequestScoped
public class OrderDetailViewCustomerRole {

	private OrderDetail orderDetail;
    @Inject
    private OrderDetailService service;
    
	public OrderDetailViewCustomerRole(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
    
    
	
}
