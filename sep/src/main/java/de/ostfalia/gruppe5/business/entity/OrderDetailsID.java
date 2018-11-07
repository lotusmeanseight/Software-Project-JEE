package de.ostfalia.gruppe5.business.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailsID implements Serializable {

    @Column(name = "orderNumber")
    private Integer orderNumber;

    @Column(name = "productCode")
    private String productCode;

    private OrderDetailsID() {
    }

    private OrderDetailsID(Integer orderNumber, String productCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsID that = (OrderDetailsID) o;
        return Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(productCode, that.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode);
    }

}
