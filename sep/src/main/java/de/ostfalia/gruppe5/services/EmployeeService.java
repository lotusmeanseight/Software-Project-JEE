package de.ostfalia.gruppe5.services;


import de.ostfalia.gruppe5.models.Employee;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    private Employee findById(Integer id) {
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

}
