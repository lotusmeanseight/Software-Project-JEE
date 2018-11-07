package de.ostfalia.gruppe5.views.comparators;

import de.ostfalia.gruppe5.business.entity.Payment;

import java.util.Comparator;

public class PaymentComparator implements Comparator<Payment> {

	@Override
	public int compare(Payment p1, Payment p2) {
        int result = p1.getCustomerNumber().getCustomerNumber().
                compareTo(p2.getCustomerNumber().getCustomerNumber());
        if (result == 0) {
            return p1.getCheckNumber().compareTo(p2.getCheckNumber());
        } else {
            return result;
        }
	}
}
