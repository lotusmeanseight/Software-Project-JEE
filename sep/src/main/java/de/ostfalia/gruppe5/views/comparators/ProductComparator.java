package de.ostfalia.gruppe5.views.comparators;

import de.ostfalia.gruppe5.business.entity.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		return p1.getProductCode().compareTo(p2.getProductCode());
	}
}
