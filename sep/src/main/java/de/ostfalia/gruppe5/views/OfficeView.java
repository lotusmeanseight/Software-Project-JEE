package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Office;
import de.ostfalia.gruppe5.services.OfficeService;

@Named
@RequestScoped
public class OfficeView {

	private Office office;

	@Inject
	private OfficeService service;

	public OfficeView() {
		office = new Office();
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Office> getOffice() {
		return service.getAllOffices();
	}

	public String save() {
		service.save(office);
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
