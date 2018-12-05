package de.ostfalia.gruppe5.business.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import de.ostfalia.gruppe5.business.boundary.validation.IBAN;

@NamedQueries({ @NamedQuery(name = "Payment.countAll", query = "SELECT COUNT(p) FROM Payment p"),
		@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p") })
@Entity
@Table(name = "payments")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Payment implements Serializable {

	@Id
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customerNumber")
	private Customer customerNumber;

	@Id
	@Size(max = 50)
	@IBAN
	private String checkNumber;

	@NotNull
	private LocalDate paymentDate;

	@NotNull
	private Double amount;

	public Customer getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Customer customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Payment payment = (Payment) o;
		return Objects.equals(getCustomerNumber(), payment.getCustomerNumber())
				&& Objects.equals(getCheckNumber(), payment.getCheckNumber())
				&& Objects.equals(getPaymentDate(), payment.getPaymentDate())
				&& Objects.equals(getAmount(), payment.getAmount());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerNumber(), getCheckNumber(), getPaymentDate(), getAmount());
	}

}
