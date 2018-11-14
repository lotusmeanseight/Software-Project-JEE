package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Employee;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class EmployeeService extends AbstractLazyJPAService<Employee> {

	public EmployeeService() {
		settClass(Employee.class);
	}

}
