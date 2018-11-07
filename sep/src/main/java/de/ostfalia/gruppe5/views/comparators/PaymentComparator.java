package de.ostfalia.gruppe5.views.comparators;

import java.util.Comparator;

import de.ostfalia.gruppe5.models.Payment;

public class PaymentComparator implements Comparator<Payment> {

	@Override
	public int compare(Payment p1, Payment p2) {
		return p1.getCheckNumber().compareTo(p2.getCheckNumber());

	}
}
