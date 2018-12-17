package de.ostfalia.gruppe5.business.boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import de.ostfalia.gruppe5.business.entity.ProductLine;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractTableJPAService<ProductLine> {

	private final List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));

	public ProductLineService() {
		setEntityClass(ProductLine.class);
	}

	public String nextID() {
		String lastID = this.getEntityManager()
				.createQuery("select MAX(p.productLine) from ProductLine p", String.class).getSingleResult();
		String[] array = lastID.split("_");
		String id = array[array.length - 1];

		int numberOfLetters = ThreadLocalRandom.current().nextInt(0, 5);

		int numbers = ThreadLocalRandom.current().nextInt(0, 10);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numberOfLetters; i++) {
			builder.append(letters.get(ThreadLocalRandom.current().nextInt(0, 26)));
		}

		for (int i = 0; i < numbers; i++) {
			builder.append(ThreadLocalRandom.current().nextInt(1, 10));
		}

		builder.append(id);

		return builder.toString();
	}
}
