package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Product;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService extends AbstractLazyJPAService<Product> {

	public ProductService() {
		settClass(Product.class);
	}

}
