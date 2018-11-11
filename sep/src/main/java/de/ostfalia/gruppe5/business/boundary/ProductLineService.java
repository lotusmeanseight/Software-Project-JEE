package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractJPAService<ProductLine> {

	public ProductLineService() {
		settClass(ProductLine.class);
	}

}
