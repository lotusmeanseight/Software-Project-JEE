package de.ostfalia.gruppe5.views;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.ProductBasket;
import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.Product;

@Named
@RequestScoped
public class ProductView {

	private Product product;

	private int quantity;

	@Inject
	private ProductBasket productBasket;

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

	public String addToBasket(Product product, int quantity) {
		if (quantity <= 0) {
			FacesContext.getCurrentInstance().addMessage("Error:",
					new FacesMessage("quantity can not " + "be zero or lower"));
		} else {
			productBasket.buyProduct(product, quantity);
		}
		return "basket?faces-redirect=true";
	}

	public ProductDataTable getDatatable() {
		return datatable;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
