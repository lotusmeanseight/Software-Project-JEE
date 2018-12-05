package de.ostfalia.gruppe5.business.boundary;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
public abstract class AbstractTableJPAService<T> extends AbstractJPAService<T> {

	public List<T> getAllEntitiesInRange(int first, int max) {
		TypedQuery<T> query = getEntityManager().createNamedQuery(getEntityClass().getSimpleName() + ".findAll",
				getEntityClass());
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}

	public int countEntities() {
		Query query = getEntityManager().createNamedQuery(getEntityClass().getSimpleName() + ".countAll");
		List<Long> resultList = query.getResultList();
		return resultList.get(0).intValue();
	}

}
