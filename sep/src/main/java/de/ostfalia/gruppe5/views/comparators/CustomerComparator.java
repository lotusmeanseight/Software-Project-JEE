package de.ostfalia.gruppe5.views.comparators;

import java.util.Comparator;

import de.ostfalia.gruppe5.models.Customer;

public class CustomerComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer cust1, Customer cust2) {
		return cust1.getCustomerNumber().compareTo(cust2.getCustomerNumber());
	}
}
