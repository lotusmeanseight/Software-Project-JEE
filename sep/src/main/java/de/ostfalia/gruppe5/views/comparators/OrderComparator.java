package de.ostfalia.gruppe5.views.comparators;

import java.util.Comparator;

import de.ostfalia.gruppe5.business.entity.Order;

public class OrderComparator implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		return o1.getOrderNumber().compareTo(o2.getOrderNumber());
	}
}
