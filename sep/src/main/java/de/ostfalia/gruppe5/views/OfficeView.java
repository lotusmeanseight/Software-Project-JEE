package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Office;
import de.ostfalia.gruppe5.services.OfficeService;

@RequestScoped
@Named
public class OfficeView {

	private Office office;
	@Inject
	private OfficeService service;

	private HtmlDataTable table;
	private int rowsOnPage = 10;

	public OfficeView() {
		setOffice(new Office());
	}

	public List<Office> getOffices() {
		return service.getAllOffices();
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
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

	public String save() {
		service.save(getOffice());
		return null;
	}

	public String delete(Office office) {
		service.deleteById(office.getOfficeCode());
		return null;
	}

	public String update(Office office) {
		service.update(office);
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
