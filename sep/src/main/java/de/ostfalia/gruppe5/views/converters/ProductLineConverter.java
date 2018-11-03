package de.ostfalia.gruppe5.views.converters;

import de.ostfalia.gruppe5.models.ProductLine;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@FacesConverter(forClass = ProductLine.class, managed = true)
public class ProductLineConverter implements Converter<ProductLine> {

    @PersistenceContext
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
