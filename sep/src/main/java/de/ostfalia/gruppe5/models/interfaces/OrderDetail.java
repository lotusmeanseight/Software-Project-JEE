package de.ostfalia.gruppe5.models.interfaces;

import java.io.Serializable;
import java.util.List;

public interface OrderDetail extends Serializable {
    Integer getOrderNumber();

    void setOrderNumber(Integer orderNumber);

    Integer getQuantityOrdered();

    void setQuantityOrdered(Integer quantityOrdered);

    Double getPriceEach();

    void setPriceEach(Double priceEach);

    Short getOrderLineNumber();

    void setOrderLineNumber(Short orderLineNumber);

    List<String> getProductCode();

    void setProductCode(List<String> productCode);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
