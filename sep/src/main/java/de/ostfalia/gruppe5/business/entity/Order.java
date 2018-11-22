package de.ostfalia.gruppe5.business.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedQueries({@NamedQuery(name = "Order.countAll", query = "SELECT COUNT(o) FROM Order o"),
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")})
@Entity
@Table(name = "orders")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    @OneToMany(mappedBy = "orderNumber")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDate orderDate;

    private LocalDate requiredDate;

    private LocalDate shippedDate;

    @Size(max = 15)
    private String status;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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

    public void setCustomerNumber(Customer customerNumber) {
        this.customerNumber = customerNumber;
    }
}
