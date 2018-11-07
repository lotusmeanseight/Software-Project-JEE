package de.ostfalia.gruppe5.business.boundary;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService {

	@PersistenceContext
	EntityManager entityManager;

	public ProductLineService() {

	}

	public void save(ProductLine productLine) {
		entityManager.persist(productLine);
	}

	public ProductLine findById(String id) {
		return entityManager.find(ProductLine.class, id);
	}

	public void deleteById(String id) {
		entityManager.remove(findById(id));
	}

	public List<ProductLine> getAllProductLines() {
		return entityManager.createQuery("select p from ProductLine p", ProductLine.class).getResultList();
	}

	public ProductLine update(ProductLine productLine) {
		return entityManager.merge(productLine);
	}

}
