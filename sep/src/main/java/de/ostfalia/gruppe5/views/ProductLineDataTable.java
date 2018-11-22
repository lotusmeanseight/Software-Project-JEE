package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class ProductLineDataTable extends Datatable<ProductLineService, ProductLine> {

	@Inject
	private ProductLineService service;

	public ProductLineDataTable() {

	}

}
