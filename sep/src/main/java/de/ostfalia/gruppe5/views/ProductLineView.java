package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.ProductLine;
import de.ostfalia.gruppe5.services.ProductLineService;

@Named
@RequestScoped
public class ProductLineView {

	private ProductLine productLine;

	@Inject
	private ProductLineService service;

	private HtmlDataTable table;
	private int rowsOnPage = 10;

	public ProductLineView() {
		productLine = new ProductLine();
	}

	public List<ProductLine> getProductLines() {
		return service.getAllProductLines();
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

	public ProductLine getProductLine() {
		return productLine;
	}

	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
	}

	public String save() {
		service.save(productLine);
		return null;
	}

	public String delete(ProductLine productLine) {
		service.deleteById(productLine.getProductLine());
		return null;
	}

	public String update(ProductLine productLine) {
		service.update(productLine);
		return null;
	}

	public void goToFirstPage() {
		table.setFirst(0);
	}

	public void goToPreviousPage() {
		table.setFirst(table.getFirst() - table.getRows());
	}

	public void goToNextPage() {
		table.setFirst(table.getFirst() + table.getRows());
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

	}

}
