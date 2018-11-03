package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Product;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService {

	@PersistenceContext
	EntityManager entityManager;

	public ProductService() {

	}

	public void save(Product product) {
		entityManager.persist(product);
	}

	public Product findById(String id) {
		return entityManager.find(Product.class, id);
	}

	public void deleteById(String id) {
		entityManager.remove(findById(id));
	}

	public List<Product> getAllProducts() {
		return entityManager.createQuery("select p from Product p", Product.class).getResultList();
	}

	public Product update(Product product) {
		return entityManager.merge(product);
	}
}
