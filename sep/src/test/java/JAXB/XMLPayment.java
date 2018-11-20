package JAXB;

import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Payment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class XMLPayment {
    private Payment payment;

    @Before
    public void setup(){
        payment = new Payment();
        payment.setAmount(111.11);
        payment.setCheckNumber("ASRA");
        Customer tempCustomer = new Customer();
        tempCustomer.setCustomerNumber(5000);
        payment.setCustomerNumber(tempCustomer);
    }


    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(Payment.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(payment, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping(){
        try {
            JAXBContext context = JAXBContext.newInstance(Payment.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = File.createTempFile("test","payment");
            marshaller.marshal(payment, file);
            Payment paymentJSON = (Payment) unmarshaller.unmarshal(file);
            Assert.assertEquals(payment, paymentJSON);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
