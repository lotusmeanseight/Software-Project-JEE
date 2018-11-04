package de.ostfalia.gruppe5.views;

import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.DataModel;
import de.ostfalia.gruppe5.models.Order;
import de.ostfalia.gruppe5.services.OrderService;

@Named
@RequestScoped
public class OrderView {

	private Order order;

	@Inject
	private OrderService service;

	private DataModel orderDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

	@PostConstruct
	public void initHashSet() {
		rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
		allRowsCount = service.countOrders(); // Zählt die Einträge in der Datenbank
		lazyDataLoading(0);
	}

	private void lazyDataLoading(int first) {
		HashSet<Order> dataHashSet = service.getAllOrdersLazy(first, rowsOnPage);
		orderDataModel = new DataModel(dataHashSet, allRowsCount, rowsOnPage);
	}

	public OrderView() {
		order = new Order();
	}

	public List<Order> getOrders() {
		return service.getAllOrders();
	}

	public String save() {
		service.save(order);
		return null;
	}

	public String delete(Order order) {
		service.deleteById(order.getOrderNumber());
		return null;
	}

	public String update(Order order) {
		service.update(order);
		return null;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public DataModel getOrderDataModel() {
		return orderDataModel;
	}

	public void setOrderDataModel(DataModel orderDataModel) {
		this.orderDataModel = orderDataModel;
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
