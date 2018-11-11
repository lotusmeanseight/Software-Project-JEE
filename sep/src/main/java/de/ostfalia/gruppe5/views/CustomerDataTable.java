package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.AbstractLazyJPAService;
import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.entity.Customer;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class CustomerDataTable extends Datatable<CustomerService, Customer> {

    @Inject
    private CustomerService service;

    public CustomerDataTable(){

    }

}
