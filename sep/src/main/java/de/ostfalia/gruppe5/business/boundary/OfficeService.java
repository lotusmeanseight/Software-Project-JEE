package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.business.entity.Office;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OfficeService extends AbstractJPAService<Office> {

	public OfficeService() {
		settClass(Office.class);
	}

}
