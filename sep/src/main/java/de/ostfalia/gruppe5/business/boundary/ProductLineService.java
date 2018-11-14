package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractLazyJPAService<ProductLine> {

	public ProductLineService() {
		settClass(ProductLine.class);
	}

}
