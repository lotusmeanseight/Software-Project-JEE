package de.ostfalia.gruppe5.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.Product;

@ViewScoped
@Named
public class ProductDataTable extends Datatable<ProductService, Product> {

	@Inject
	private ProductService service;

	public ProductDataTable() {
	}

}
