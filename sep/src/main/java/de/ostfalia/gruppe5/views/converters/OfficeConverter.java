package de.ostfalia.gruppe5.views.converters;

import de.ostfalia.gruppe5.models.Office;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@FacesConverter(forClass = Office.class, managed = true)
public class OfficeConverter implements Converter<Office> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Office getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        Integer officeID = Integer.parseInt(s);
        return entityManager.find(Office.class, officeID);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Office office) {
        if (office == null) {
            return "";
        }

        return office.getOfficeCode().toString();
    }
}
