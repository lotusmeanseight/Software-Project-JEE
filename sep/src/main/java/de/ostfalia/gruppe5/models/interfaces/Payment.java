package de.ostfalia.gruppe5.models.interfaces;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Payment extends Serializable {
    Customer getCustomerNumber();

    void setCustomerNumber(Customer customerNumber);

    String getCheckNumber();

    void setCheckNumber(String checkNumber);

    LocalDateTime getPaymentDate();

    void setPaymentDate(LocalDateTime paymentDate);

    Double getAmount();

    void setAmount(Double amount);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
