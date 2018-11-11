package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.entity.Office;
import de.ostfalia.gruppe5.business.boundary.OfficeService;

@RequestScoped
@Named
public class OfficeView {

	private Office office;
	@Inject
	private OfficeService service;


	public OfficeView() {
		setOffice(new Office());
	}

	public List<Office> getOffices() {
		return service.findAll();
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
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
}
