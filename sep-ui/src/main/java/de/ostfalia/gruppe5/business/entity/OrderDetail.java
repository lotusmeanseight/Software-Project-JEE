package de.ostfalia.gruppe5.business.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "orderdetails")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class OrderDetail implements Serializable {

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orderNumber")
	private Order orderNumber;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
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

	public Order getOrderNumber() {
		return orderNumber;
	}

	public Product getProductCode() {
		return productCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderDetail that = (OrderDetail) o;
		return Objects.equals(orderNumber, that.orderNumber) && Objects.equals(productCode, that.productCode)
				&& Objects.equals(quantityOrdered, that.quantityOrdered) && Objects.equals(priceEach, that.priceEach)
				&& Objects.equals(orderLineNumber, that.orderLineNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber);
	}

	public void setOrderNumber(Order orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setProductCode(Product productCode) {
		this.productCode = productCode;
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
