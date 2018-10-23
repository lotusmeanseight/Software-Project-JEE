package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetail implements Serializable {

    @OneToOne
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Order orderNumber;

    @OneToMany
    @Size(max = 15)
    @PrimaryKeyJoinColumn
    private List<Product> productCode;
    private Integer quantityOrdered;
    private Double priceEach;
    private Short orderLineNumber;

    public Order getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Order orderNumber) {
        this.orderNumber = orderNumber;
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

    public List<Product> getProductCode() {
        return productCode;
    }

    public void setProductCode(List<Product> productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(getOrderNumber(), that.getOrderNumber())
                && Objects.equals(getProductCode(), that.getProductCode())
                && Objects.equals(getQuantityOrdered(), that.getQuantityOrdered())
                && Objects.equals(getPriceEach(), that.getPriceEach())
                && Objects.equals(getOrderLineNumber(), that.getOrderLineNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber(), getProductCode(), getQuantityOrdered(), getPriceEach(),
                getOrderLineNumber());
    }
}
