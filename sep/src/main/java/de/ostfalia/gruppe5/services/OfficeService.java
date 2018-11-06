package de.ostfalia.gruppe5.services;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.models.Office;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OfficeService {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	public OfficeService() {

	}

	public void save(Office office) {
		entityManager.persist(office);
	}

	public Office findById(Integer id) {
		return entityManager.find(Office.class, id);
	}

	public void deleteById(Integer id) {
		entityManager.remove(findById(id));
	}

	public void delete(Office office) {
		entityManager.remove(entityManager.merge(office));
	}

	public List<Office> getAllOffices() {
		return entityManager.createQuery("select o from Office o", Office.class).getResultList();
	}

	public Office update(Office office) {
		return entityManager.merge(office);
	}

}
