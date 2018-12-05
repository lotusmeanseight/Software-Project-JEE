package de.ostfalia.gruppe5.views.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.business.entity.Employee;

@FacesConverter(forClass = Employee.class, managed = true)
public class EmployeeConverter implements Converter<Employee> {

	@PersistenceContext(unitName = "simple")
	private EntityManager entityManager;

	@Override
	public Employee getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}

		Integer employeeID = Integer.parseInt(s);
		return entityManager.find(Employee.class, employeeID);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Employee employee) {
		if (employee == null) {
			return "";
		}

		return employee.getEmployeeNumber().toString();
	}
}
