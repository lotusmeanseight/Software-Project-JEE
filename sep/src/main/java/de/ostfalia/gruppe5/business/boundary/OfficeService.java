package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.Office;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OfficeService extends AbstractLazyJPAService<Office> {

	public OfficeService() {
		settClass(Office.class);
	}

}
