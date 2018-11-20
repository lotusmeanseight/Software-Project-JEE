package de.ostfalia.gruppe5.business.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService extends AbstractLazyJPAService<Product> {

	public ProductService() {
		setEntityClass(Product.class);
	}

}
