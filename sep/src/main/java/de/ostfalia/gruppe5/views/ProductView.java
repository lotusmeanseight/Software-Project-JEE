package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.DataModel;
import de.ostfalia.gruppe5.business.entity.Product;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ProductView {

	private Product product;

	@Inject
	private ProductService service;

	private DataModel productDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

	public ProductView() {
		product = new Product();
	}

	public DataModel getProductDataModel() {
		return productDataModel;
	}

	public void setProductDataModel(DataModel productDataModel) {
		this.productDataModel = productDataModel;
	}

	public HtmlDataTable getTable() {
		return table;
	}

	public void setTable(HtmlDataTable table) {
		this.table = table;
	}

	public int getRowsOnPage() {
		return rowsOnPage;
	}

	public void setRowsOnPage(int rowsOnPage) {
		this.rowsOnPage = rowsOnPage;
	}

	public int getAllRowsCount() {
		return allRowsCount;
	}

	public void setAllRowsCount(int allRowsCount) {
		this.allRowsCount = allRowsCount;
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
}
