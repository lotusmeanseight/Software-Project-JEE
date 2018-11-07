package de.ostfalia.gruppe5.views.comparators;

import java.util.Comparator;

import de.ostfalia.gruppe5.models.Product;

public class ProductComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		return p1.getProductCode().compareTo(p2.getProductCode());
	}
}
