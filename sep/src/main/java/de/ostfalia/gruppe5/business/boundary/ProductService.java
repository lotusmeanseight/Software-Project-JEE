package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Product;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@RolesAllowed({"EMPLOYEE","CUSTOMER"})
@Stateless
public class ProductService extends AbstractTableJPAService<Product> {

	public ProductService() {
		setEntityClass(Product.class);
	}

	public String nextID(){
		String lastID = this.getEntityManager().createQuery("select MAX(p.productCode) from Product p", String.class).getSingleResult();
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
		return sb.toString();
	}

}
