package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractLazyJPAService<ProductLine> {

	public ProductLineService() {
		setEntityClass(ProductLine.class);
	}

	public String nextID(){
		String lastID = this.getEntityManager().createQuery("select MAX(p.productLine) from ProductLine p", String.class).getSingleResult();
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
}
