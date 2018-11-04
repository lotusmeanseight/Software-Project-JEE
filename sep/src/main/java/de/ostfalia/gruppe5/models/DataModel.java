package de.ostfalia.gruppe5.models;

import java.util.HashSet;

import javax.faces.model.CollectionDataModel;

public class DataModel extends CollectionDataModel {

	private int rowIndex = -1;
	private int allRowsCount;
	private int pageSize;
	private HashSet hashSet;

	public DataModel() {
	}

	public DataModel(HashSet hashSet, int allRowsCount, int pageSize) {
		this.hashSet = hashSet;
		this.allRowsCount = allRowsCount;
		this.pageSize = pageSize;
	}

	@Override
	public boolean isRowAvailable() {
		if (hashSet == null) {
			return false;
		}
		int c_rowIndex = getRowIndex();
		if (c_rowIndex >= 0 && c_rowIndex < hashSet.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getRowCount() {
		return allRowsCount;
	}

	@Override
	public Object getRowData() {
		if (hashSet == null) {
			return null;
		} else if (!isRowAvailable()) {
			throw new IllegalArgumentException();
		} else {
			int dataIndex = getRowIndex();
			Object[] arrayView = hashSet.toArray();
			return arrayView[dataIndex];
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
		return hashSet;
	}

	@Override
	public void setWrappedData(Object hashSet) {
		this.hashSet = (HashSet) hashSet;
	}
}
