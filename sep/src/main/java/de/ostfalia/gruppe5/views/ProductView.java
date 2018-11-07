package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.DataModel;
import de.ostfalia.gruppe5.models.Product;
import de.ostfalia.gruppe5.services.ProductService;

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

	@PostConstruct
	public void initHashSet() {
		rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
		allRowsCount = service.countProducts(); // Zählt die Einträge in der Datenbank
		lazyDataLoading(0);
	}

	private void lazyDataLoading(int first) {
		TreeSet<Product> dataTreeSet = service.getAllProductsLazy(first, rowsOnPage);
		productDataModel = new DataModel(dataTreeSet, allRowsCount, rowsOnPage);
	}

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

	public void goToFirstPage() {
		table.setFirst(0);
		lazyDataLoading(0);
	}

	public void goToPreviousPage() {
		table.setFirst(table.getFirst() - table.getRows());
		lazyDataLoading(table.getFirst());
	}

	public void goToNextPage() {
		table.setFirst(table.getFirst() + table.getRows());
		lazyDataLoading(table.getFirst());
	}

	public void goToLastPage() {
		int totalRows = table.getRowCount();
		int displayRows = table.getRows();
		int full = totalRows / displayRows;
		int modulo = totalRows % displayRows;

		if (modulo > 0) {
			table.setFirst(full * displayRows);
		} else {
			table.setFirst((full - 1) * displayRows);
		}

		lazyDataLoading(table.getFirst());
	}

}
