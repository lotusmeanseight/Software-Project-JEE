package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.implementations.OrderDetailImpl;
import de.ostfalia.gruppe5.models.interfaces.OrderDetail;
import de.ostfalia.gruppe5.services.OrderDetailService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OrderDetailView {
    private OrderDetail orderDetail;
    @Inject
    private OrderDetailService service;

    public OrderDetailView(){
        this.orderDetail = new OrderDetailImpl();
    }

    public String save() {
        service.save(orderDetail);
        return null;
    }

    public String update(OrderDetail order) {
        service.update(order);
        return null;
    }

    public OrderDetail getOrder() {
        return orderDetail;
    }

    public void setOrder(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
