package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractLazyJPAService<ProductLine> {

	public ProductLineService() {
		settClass(ProductLine.class);
	}

}
