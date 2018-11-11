package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ProductLineView {

	private ProductLine productLine;

	@Inject
	private ProductLineService service;


	public ProductLineView() {
		productLine = new ProductLine();
	}

	public List<ProductLine> getProductLines() {
		return service.findAll();
	}

	public String save() {
		service.save(productLine);
		return null;
	}

	public String delete(ProductLine productLine) {
		service.deleteById(productLine.getProductLine());
		return null;
	}

	public String update(ProductLine productLine) {
		service.update(productLine);
		return null;
	}
}
