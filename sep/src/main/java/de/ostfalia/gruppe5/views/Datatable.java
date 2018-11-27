package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.AbstractTableJPAService;
import de.ostfalia.gruppe5.business.entity.DataModel;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public abstract class Datatable<T extends AbstractTableJPAService, E> implements Serializable {

    private DataModel dataModel;
    @Transient
    private HtmlDataTable table;
    private int rowsOnPage;
    private int allRowsCount = 0;
    @Inject
    private T service;

    public Datatable(){

    }

    @PostConstruct
    public void initList() {
        rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
        allRowsCount = service.countEntities(); // Zählt die Einträge in der Datenbank
        lazyDataLoading(0);
    }

    private void lazyDataLoading(int first) {
        List<E> lazyList = service.getAllEntitiesInRange(first, rowsOnPage);
        dataModel = new DataModel(lazyList, allRowsCount, rowsOnPage);
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

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public int getRowsOnPage() {
        return rowsOnPage;
    }

    public void setRowsOnPage(int rowsOnPage) {
        this.rowsOnPage = rowsOnPage;
    }

    public HtmlDataTable getTable() {
        return table;
    }

    public void setTable(HtmlDataTable table) {
        this.table = table;
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
