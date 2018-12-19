package de.ostfalia.gruppe5.views.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = LocalDate.class, managed = true)
public class LocalDateConverter implements Converter<LocalDate> {

	private static final String FORMAT = "yyyy-MM-dd";

	@Override
	public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		return LocalDate.parse(s, formatter);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
		return localDate.toString();
	}
}
