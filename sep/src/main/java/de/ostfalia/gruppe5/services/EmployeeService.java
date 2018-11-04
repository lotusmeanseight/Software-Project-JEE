package de.ostfalia.gruppe5.services;

import java.util.HashSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.ostfalia.gruppe5.models.Employee;

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

	public HashSet<Employee> getAllEmployeesLazy(int first, int max) {
		Query query = entityManager.createNamedQuery("Employee.findAll");
		query.setFirstResult(first);
		query.setMaxResults(max);
		return new HashSet(query.getResultList());
	}

	public int countEmployees() {
		Query query = entityManager.createNamedQuery("Employee.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}

}
