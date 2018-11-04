package de.ostfalia.gruppe5.services;

import java.util.HashSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.ostfalia.gruppe5.models.Product;

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

	public HashSet<Product> getAllProductsLazy(int first, int max) {
		Query query = entityManager.createNamedQuery("Product.findAll");
		query.setFirstResult(first);
		query.setMaxResults(max);
		return new HashSet(query.getResultList());
	}

	public int countProducts() {
		Query query = entityManager.createNamedQuery("Product.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}

}
