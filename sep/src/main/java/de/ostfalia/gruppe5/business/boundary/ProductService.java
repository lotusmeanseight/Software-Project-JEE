package de.ostfalia.gruppe5.business.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductService extends AbstractLazyJPAService<Product> {

	public ProductService() {
		setEntityClass(Product.class);
	}

	public String nextID(){
		String lastID = this.getEntityManager().createQuery("select MAX(p.productCode) from Product p", String.class).getSingleResult();
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
