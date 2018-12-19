package de.ostfalia.gruppe5.business.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({ @NamedQuery(name = "Order.countAll", query = "SELECT COUNT(o) FROM Order o"),
		@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o") })
@Entity
@Table(name = "orders")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Order {

	@Id
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

	/*
	 * @Override public String toString() { final StringBuilder sb = new
	 * StringBuilder();
	 * Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> { if
	 * (!field.getName().equals("orderDetails") &&
	 * !field.getName().equals("customerNumber")) { try { sb.append(", ");
	 * sb.append(field.getName()); sb.append("="); sb.append(field.get(this)); }
	 * catch (IllegalAccessException e) { e.printStackTrace(); } } });
	 * sb.append("]"); String toString = "[" + sb.toString().subSequence(2,
	 * sb.length()); return toString; }
	 */

	@Override
	public String toString() {
		return "Order{" + "orderNumber=" + orderNumber + ", orderDetails=" + orderDetails + ", orderDate=" + orderDate
				+ ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate + ", status='" + status + '\''
				+ ", comments='" + comments + '\'' + ", customerNumber=" + customerNumber + '}';
	}


	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
			try {
				sb.append("\"");
				sb.append(field.getName());
				sb.append("\"");
				sb.append(":");
				sb.append("\"");
				sb.append(field.get(this));
				sb.append("\"");
				sb.append(", ");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return sb.substring(0, sb.length() - 1) + "}";
	}
}
