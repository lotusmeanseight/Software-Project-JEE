package de.ostfalia.gruppe5.xml;

import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class XMLCustomer {

    private Customer customer;

    @Before
    public void setup() {
        customer = new Customer();
        Employee tempEmployee = new Employee();
        tempEmployee.setEmployeeNumber(500);
        customer.setSalesRepEmployeeNumber(tempEmployee);
        customer.setAddressLine1("A");
        customer.setAddressLine2("B");
        customer.setContactFirstName("Jeff");
        customer.setContactLastName("Bart");
        customer.setCountry("Deutschland");
        customer.setPhone("111");
        customer.setPostalCode("112");
        customer.setCreditLimit(111.111d);
        customer.setState("Niedersachsen");
        customer.setCustomerName("Professor");
        customer.setCity("Braunschweig");
    }

    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(Customer.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(customer, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping() {
            try {
                JAXBContext context = JAXBContext.newInstance(Customer.class);
                Marshaller marshaller = context.createMarshaller();
                Unmarshaller unmarshaller = context.createUnmarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                File file = File.createTempFile("test", "customer");
                marshaller.marshal(customer, file);
                Customer customerJSON = (Customer) unmarshaller.unmarshal(file);
                Assert.assertEquals(customer, customerJSON);
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

