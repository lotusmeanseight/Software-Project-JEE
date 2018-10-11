package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class CustomerService {

    @PersistenceContext(unitName = "simple")
    EntityManager em;


    public CustomerService() {
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    private Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c from Customer c", Customer.class).getResultList();
    }


    public Customer update(Customer customer) {
        Customer detachedCustomer = this.findById(customer.getCustomerNumber());
        detachedCustomer.setAddressLine1(customer.getAddressLine1());
        detachedCustomer.setAddressLine2(customer.getAddressLine2());
        detachedCustomer.setCity(customer.getCity());
        detachedCustomer.setContactFirstName(customer.getContactFirstName());
        detachedCustomer.setContactLastName(customer.getContactLastName());
        detachedCustomer.setCountry(customer.getCountry());
        detachedCustomer.setCreditLimit(customer.getCreditLimit());
        detachedCustomer.setCustomerName(customer.getCustomerName());
        detachedCustomer.setCustomerNumber(customer.getCustomerNumber());
        detachedCustomer.setPhone(customer.getPhone());
        detachedCustomer.setPostalCode(customer.getPostalCode());
        detachedCustomer.setSalesRepEmployeeNumber(customer.getSalesRepEmployeeNumber());
        detachedCustomer.setState(customer.getState());

        return detachedCustomer;
    }
}