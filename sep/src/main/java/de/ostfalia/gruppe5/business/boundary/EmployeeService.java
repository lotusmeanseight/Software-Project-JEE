package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Employee;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class EmployeeService extends AbstractTableJPAService<Employee> {

	@Override
	public void save(Employee entity) {
		TypedQuery<Integer> customerTypedQuery = getEntityManager()
				.createQuery("select MAX(e.employeeNumber) " + "from Employee e", Integer.class);
		entity.setEmployeeNumber(customerTypedQuery.getResultList().get(0) + 1);
		super.save(super.update(entity));
	}

	public Integer nextID() {
		Integer lastID = this.getEntityManager()
				.createQuery("select MAX(e.employeeNumber) from Employee e", Integer.class).getSingleResult();
		lastID++;

		return lastID;
	}

	public EmployeeService() {
		setEntityClass(Employee.class);
	}

}
