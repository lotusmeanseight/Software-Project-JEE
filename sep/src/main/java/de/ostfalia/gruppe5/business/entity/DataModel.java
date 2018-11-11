package de.ostfalia.gruppe5.business.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.faces.model.CollectionDataModel;

public class DataModel<E> extends CollectionDataModel<E> {

	private int rowIndex = -1;
	private int allRowsCount;
	private int pageSize;
	private List<E> list;

	public DataModel() {
	}

	public DataModel(List<E> list, int allRowsCount, int pageSize) {
		this.list = list;
		this.allRowsCount = allRowsCount;
		this.pageSize = pageSize;
	}

	@Override
	public boolean isRowAvailable() {
		return list != null && getRowIndex() >= 0 && getRowIndex() < list.size();
	}

	@Override
	public int getRowCount() {
		return allRowsCount;
	}

	@Override
	public E getRowData() {
		if(!isRowAvailable()){
			throw new IllegalArgumentException();
		}else{
			return list.get(getRowIndex());
		}
	}

	@Override
	public int getRowIndex() {
		return (rowIndex % pageSize);
	}

	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public Object getWrappedData() {
		return list;
	}

	@Override
	public void setWrappedData(Object list) {
		this.list = (ArrayList<E>) list;
	}
}
