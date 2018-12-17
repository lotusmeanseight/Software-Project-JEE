package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.Order;

@Named
@RequestScoped
public class OrderViewCustomerRole {

	private Order order;

	@Inject
	CustomerUser user;

	private HtmlDataTable table;
	private int rowsOnPage = 10;

	@Inject
	private OrderService service;

	public List<Order> getOrders() {
		return service.getOrdersByCustomerId(user.getId());
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
