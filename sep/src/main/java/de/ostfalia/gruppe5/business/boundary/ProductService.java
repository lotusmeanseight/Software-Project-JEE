package de.ostfalia.gruppe5.business.boundary;

import java.util.TreeSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.views.comparators.ProductComparator;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService extends AbstractLazyJPAService<Product> {

	public ProductService() {
		settClass(Product.class);
	}

}
