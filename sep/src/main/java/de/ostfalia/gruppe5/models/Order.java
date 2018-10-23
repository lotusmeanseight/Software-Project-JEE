package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    private LocalDateTime orderDate;

    private LocalDateTime requiredDate;

    private LocalDateTime shippedDate;

    @Size(max = 15)
    private String status;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customer customerNumber;

    public Order() {

    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDateTime requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDateTime shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCustomerNumber() {
        return customerNumber.getCustomerNumber();
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber.setCustomerNumber(customerNumber);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(getOrderNumber(), order.getOrderNumber())
                && Objects.equals(getRequiredDate(), order.getRequiredDate())
                && Objects.equals(getShippedDate(), order.getShippedDate())
                && Objects.equals(getOrderDate(), order.getOrderDate())
                && Objects.equals(getStatus(), order.getStatus()) && Objects.equals(getComments(), order.getComments())
                && Objects.equals(getCustomerNumber(), order.getCustomerNumber());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber(), getOrderDate(), getRequiredDate(), getShippedDate(), getStatus(),
                getComments(), getCustomerNumber());
    }
}
