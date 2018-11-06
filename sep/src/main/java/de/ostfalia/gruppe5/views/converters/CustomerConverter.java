package de.ostfalia.gruppe5.views.converters;

import de.ostfalia.gruppe5.models.Customer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@FacesConverter(forClass = Customer.class, managed = true)
public class CustomerConverter implements Converter<Customer> {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        Integer customerID = Integer.parseInt(s);
        return entityManager.find(Customer.class, customerID);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Customer customer) {
        if (customer == null) {
            return "";
        }
        return customer.getCustomerNumber().toString();
    }
}
