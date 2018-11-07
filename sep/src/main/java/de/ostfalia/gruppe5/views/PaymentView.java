package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.DataModel;
import de.ostfalia.gruppe5.models.Payment;
import de.ostfalia.gruppe5.services.PaymentService;

@Named
@RequestScoped
public class PaymentView {

	private Payment payment;
	@Inject
	private PaymentService service;

	private DataModel paymentDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

	@PostConstruct
	public void initSet() {
		rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
		allRowsCount = service.countPayments(); // Zählt die Einträge in der Datenbank
		lazyDataLoading(0);
	}

	private void lazyDataLoading(int first) {
		TreeSet<Payment> dataTreeSet = service.getAllPaymentsLazy(first, rowsOnPage);
		paymentDataModel = new DataModel(dataTreeSet, allRowsCount, rowsOnPage);
	}

	public PaymentView() {
		payment = new Payment();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public DataModel getPaymentDataModel() {
		return paymentDataModel;
	}

	public void setPaymentDataModel(DataModel paymentDataModel) {
		this.paymentDataModel = paymentDataModel;
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

	public String save() {
		service.save(payment);
		return null;
	}

	public String update(Payment payment) {
		service.update(payment);
		return null;
	}

	public String delete(Payment payment) {
		service.delete(payment);
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
