package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Office;
import de.ostfalia.gruppe5.services.OfficeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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
		return service.getAllOffices();
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


    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
