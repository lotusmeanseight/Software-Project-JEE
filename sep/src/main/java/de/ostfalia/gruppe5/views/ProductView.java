package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.interfaces.Product;
import de.ostfalia.gruppe5.models.implementations.ProductImpl;
import de.ostfalia.gruppe5.services.ProductService;

@Named
@RequestScoped
public class ProductView {

	private Product product;

	@Inject
	private ProductService service;

	public ProductView() {
		product = new ProductImpl();
	}

	public List<Product> getProducts() {
		return service.getAllProducts();
	}

	public String save() {
		service.save(product);
		return null;
	}

	public String delete(Product product) {
		service.deleteById(product.getProductLine());
		return null;
	}

	public String update(Product product) {
		service.update(product);
		return null;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
