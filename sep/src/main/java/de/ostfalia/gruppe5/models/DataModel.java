package de.ostfalia.gruppe5.models;

import java.util.TreeSet;

import javax.faces.model.CollectionDataModel;

public class DataModel extends CollectionDataModel {

	private int rowIndex = -1;
	private int allRowsCount;
	private int pageSize;
	private TreeSet treeSet;

	public DataModel() {
	}

	public DataModel(TreeSet treeSet, int allRowsCount, int pageSize) {
		this.treeSet = treeSet;
		this.allRowsCount = allRowsCount;
		this.pageSize = pageSize;
	}

	@Override
	public boolean isRowAvailable() {
		if (treeSet == null) {
			return false;
		}
		int c_rowIndex = getRowIndex();
		if (c_rowIndex >= 0 && c_rowIndex < treeSet.size()) {
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
		if (treeSet == null) {
			return null;
		} else if (!isRowAvailable()) {
			throw new IllegalArgumentException();
		} else {
			int dataIndex = getRowIndex();
			Object[] arrayView = treeSet.toArray();
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
		return treeSet;
	}

	@Override
	public void setWrappedData(Object treeSet) {
		this.treeSet = (TreeSet) treeSet;
	}
}
