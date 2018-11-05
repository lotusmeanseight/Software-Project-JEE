package de.ostfalia.gruppe5.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({ @NamedQuery(name = "Payment.countAll", query = "SELECT COUNT(p) FROM Payment p"),
		@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p") })
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

	@Id
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customerNumber")
	private Customer customerNumber;

	@Id
	@Size(max = 50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String checkNumber;

	@NotNull
	private LocalDateTime paymentDate;

	@NotNull
	private Double amount;

	public Integer getCustomerNumber() {
		return customerNumber.getCustomerNumber();
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
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
