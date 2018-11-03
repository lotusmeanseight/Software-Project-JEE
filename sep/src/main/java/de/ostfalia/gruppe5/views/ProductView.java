package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Product;
import de.ostfalia.gruppe5.services.ProductService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ProductView {

	private Product product;

	@Inject
	private ProductService service;

	public ProductView() {
		product = new Product();
	}

	public List<Product> getProducts() {
		return service.getAllProducts();
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
