package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.OrderDetailService;
import de.ostfalia.gruppe5.business.entity.OrderDetail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class OrderDetailViewCustomerRole {

	private OrderDetail orderDetail;
	
    @Inject
    private OrderDetailService service;
    
    @Inject 
    private OrderDataTable datatable;

    public OrderDetailViewCustomerRole() {
    }
    
	public OrderDetailViewCustomerRole(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

    public List<OrderDetail> getOrderDetails(Integer orderNumber) {
        return service.getAllOrderDetails(orderNumber);
    }

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
    
    
	
}
