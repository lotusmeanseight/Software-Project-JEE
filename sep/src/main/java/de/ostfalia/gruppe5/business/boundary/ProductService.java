package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.Product;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService extends AbstractLazyJPAService<Product> {

	public ProductService() {
		settClass(Product.class);
	}

}
