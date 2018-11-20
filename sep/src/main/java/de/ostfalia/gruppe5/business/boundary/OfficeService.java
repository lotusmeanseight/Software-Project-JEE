package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Office;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@RolesAllowed("EMPLOYEE")
@Stateless
public class OfficeService extends AbstractLazyJPAService<Office> {

	@Override
	public void save(Office entity) {
		TypedQuery<Integer> customerTypedQuery = getEntityManager().createQuery("select MAX(o.customerNumber) " +
				"from Office o", Integer.class);
		entity.setOfficeCode(customerTypedQuery.getResultList().get(0)+1);
		super.save(super.update(entity));
	}

	public OfficeService() {
		setEntityClass(Office.class);
	}

}
