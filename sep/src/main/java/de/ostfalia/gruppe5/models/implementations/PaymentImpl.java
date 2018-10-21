package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Customer;
import de.ostfalia.gruppe5.models.interfaces.Payment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class PaymentImpl implements Payment {
	
	@Id
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customerNumber")
	private Customer customerNumber;
	
	@Id
	@Size(max=50)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String checkNumber;
	
	@NotNull
	private LocalDateTime paymentDate;
	
	@NotNull
	private Double amount;

	@Override
	public Customer getCustomerNumber() {
		return customerNumber;
	}

	@Override
	public void setCustomerNumber(Customer customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Override
	public String getCheckNumber() {
		return checkNumber;
	}

	@Override
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@Override
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	@Override
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public Double getAmount() {
		return amount;
	}

	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Payment payment = (Payment) o;
		return Objects.equals(getCustomerNumber(), payment.getCustomerNumber()) &&
				Objects.equals(getCheckNumber(), payment.getCheckNumber()) &&
				Objects.equals(getPaymentDate(), payment.getPaymentDate()) &&
				Objects.equals(getAmount(), payment.getAmount());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerNumber(), getCheckNumber(), getPaymentDate(), getAmount());
	}
}
