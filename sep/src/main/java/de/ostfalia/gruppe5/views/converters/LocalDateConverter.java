package de.ostfalia.gruppe5.views.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter(forClass = LocalDate.class, managed = true)
public class LocalDateConverter implements Converter<LocalDate> {

    private static final String FORMAT = "yyyy-mm-dd";

    @Override
    public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate localDate = LocalDate.parse(s, formatter);
        return localDate;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
        return localDate.toString();
    }
}
