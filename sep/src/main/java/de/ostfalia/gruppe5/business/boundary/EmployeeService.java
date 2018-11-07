package de.ostfalia.gruppe5.business.boundary;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Employee;
import de.ostfalia.gruppe5.views.comparators.EmployeeComparator;

@RolesAllowed("EMPLOYEE")
@Stateless
public class EmployeeService {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	public EmployeeService() {

	}

	public void save(Employee employee) {
		entityManager.persist(employee);
	}

	public Employee findById(Integer id) {
		return entityManager.find(Employee.class, id);
	}

	public void deleteById(Integer id) {
		entityManager.remove(findById(id));
	}

	public List<Employee> getAllEmployees() {
		return entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
	}

	public Employee update(Employee employee) {
		return entityManager.merge(employee);
	}

	public TreeSet<Employee> getAllEmployeesLazy(int first, int max) {
		TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
		query.setFirstResult(first);
		query.setMaxResults(max);
		TreeSet<Employee> treeSet = new TreeSet<Employee>(new EmployeeComparator());
		for (Employee e : query.getResultList()) {
			treeSet.add(e);
		}
		return treeSet;
	}

	public int countEmployees() {
		Query query = entityManager.createNamedQuery("Employee.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}

}
