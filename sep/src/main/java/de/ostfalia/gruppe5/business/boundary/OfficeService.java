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
		TypedQuery<Integer> customerTypedQuery = getEntityManager().createQuery("select MAX(o.officeCode) " +
				"from Office o", Integer.class);
		entity.setOfficeCode(customerTypedQuery.getResultList().get(0)+1);
		super.save(super.update(entity));
	}

	public String nextID(){
		String lastID = this.getEntityManager().createQuery("select MAX(o.officeCode) from Office o", String.class).getSingleResult();
		System.out.println("lastID:"+lastID);
		String[] array = lastID.split("_");
		String id = array[array.length-1];
		Integer nextID = Integer.parseInt(id);
		nextID++;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length-1; i++) {
			sb.append(array[i]);
			sb.append("_");
		}
		sb.append(nextID);
		System.out.println("nextID:"+sb.toString());
		return sb.toString();
	}

	public OfficeService() {
		setEntityClass(Office.class);
	}

}
