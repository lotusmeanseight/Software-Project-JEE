package de.ostfalia.gruppe5.views.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.business.entity.ProductLine;

@FacesConverter(forClass = ProductLine.class, managed = true)
public class ProductLineConverter implements Converter<ProductLine> {

	@PersistenceContext(unitName = "simple")
	private EntityManager entityManager;

	@Override
	public ProductLine getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}

		return entityManager.find(ProductLine.class, s);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, ProductLine productLine) {
		if (productLine == null) {
			return "";
		}

		return productLine.toString();
	}
}
