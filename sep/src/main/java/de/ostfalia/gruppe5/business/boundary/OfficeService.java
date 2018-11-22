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
		TypedQuery<Integer> officeTypedQuery = getEntityManager().createQuery("select MAX(o.officeCode) " +
				"from Office o", Integer.class);
		entity.setOfficeCode(officeTypedQuery.getResultList().get(0)+1);
		super.save(super.update(entity));
	}

	public Integer nextID(){
		Integer lastID = this.getEntityManager().createQuery("select MAX(o.officeCode) from Office o", Integer.class).getSingleResult();
		lastID++;
		return lastID;
	}

	public OfficeService() {
		setEntityClass(Office.class);
	}

}
