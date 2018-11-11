package de.ostfalia.gruppe5.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.Product;

@Named
@RequestScoped
public class ProductView {

	private Product product;

	@Inject
	private ProductService service;

	@Inject
	private ProductDataTable datatable;

	public ProductView() {
		product = new Product();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String save() {
		service.save(product);
		return null;
	}

	public String delete(Product product) {
		service.deleteById(product.getProductCode());
		return null;
	}

	public String update(Product product) {
		service.update(product);
		return null;
	}

	public ProductDataTable getDatatable() {
		return datatable;
	}

}
