package de.ostfalia.gruppe5.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.OfficeService;
import de.ostfalia.gruppe5.business.entity.Office;

@ViewScoped
@Named
public class OfficeDataTable extends Datatable<OfficeService, Office> {

	@Inject
	private OfficeService service;

	public OfficeDataTable() {

	}
}
