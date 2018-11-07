package de.ostfalia.gruppe5.business.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetail implements Serializable {

    @EmbeddedId
    private OrderDetailsID id;

    @ManyToOne
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private Order orderNumber;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product productCode;

    private Integer quantityOrdered;
    private Double priceEach;
    private Short orderLineNumber;


    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Double getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(Double priceEach) {
        this.priceEach = priceEach;
    }

    public Short getOrderLineNumber() {
        return orderLineNumber;
    }

    public void setOrderLineNumber(Short orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }

    public OrderDetailsID getId() {
        return id;
    }

    public Order getOrderNumber() {
        return orderNumber;
    }

    public Product getProductCode() {
        return productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(quantityOrdered, that.quantityOrdered) &&
                Objects.equals(priceEach, that.priceEach) &&
                Objects.equals(orderLineNumber, that.orderLineNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber);
    }

    public void setOrderNumber(Order orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

    public void setId(OrderDetailsID id) {
        this.id = id;
    }
}
