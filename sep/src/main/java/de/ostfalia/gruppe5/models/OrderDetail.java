package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "orderdetails")
public class OrderDetail implements Serializable {

    @Id
    @OneToOne
    private Order orderNumber;

    @Id
    @ManyToOne
    @Size(max = 15)
    private Product productCode;
    private Integer quantityOrdered;
    private Double priceEach;
    private Short orderLineNumber;

    public Order getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Order orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Product getProductCode() {
        return productCode;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

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
}
