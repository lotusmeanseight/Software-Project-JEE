package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.OrderDetail;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetailImpl implements OrderDetail {

	@OneToOne
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNumber;
	@OneToMany
	@Size(max = 15)
	@PrimaryKeyJoinColumn
	private List<String> productCode;
	private Integer quantityOrdered;
	private Double priceEach;
	private Short orderLineNumber;

	@Override
	public Integer getOrderNumber() {
		return orderNumber;
	}

	@Override
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public Integer getQuantityOrdered() {
		return quantityOrdered;
	}

	@Override
	public void setQuantityOrdered(Integer quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	@Override
	public Double getPriceEach() {
		return priceEach;
	}

	@Override
	public void setPriceEach(Double priceEach) {
		this.priceEach = priceEach;
	}

	@Override
	public Short getOrderLineNumber() {
		return orderLineNumber;
	}

	@Override
	public void setOrderLineNumber(Short orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

	@Override
	public List<String> getProductCode() {
		return productCode;
	}

	@Override
	public void setProductCode(List<String> productCode) {
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
