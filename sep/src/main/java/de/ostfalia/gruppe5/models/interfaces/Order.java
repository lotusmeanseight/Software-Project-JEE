package de.ostfalia.gruppe5.models.interfaces;

import java.time.LocalDateTime;

public interface Order {
    Integer getOrderNumber();

    void setOrderNumber(Integer orderNumber);

    LocalDateTime getRequiredDate();

    void setRequiredDate(LocalDateTime requiredDate);

    LocalDateTime getShippedDate();

    void setShippedDate(LocalDateTime shippedDate);

    String getStatus();

    void setStatus(String status);

    String getComments();

    void setComments(String comments);

    Customer getCustomerNumber();

    void setCustomerNumber(Customer customerNumber);

    LocalDateTime getOrderDate();

    void setOrderDate(LocalDateTime orderDate);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
