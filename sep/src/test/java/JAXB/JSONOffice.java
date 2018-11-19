package JAXB;

import de.ostfalia.gruppe5.business.entity.Office;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class JSONOffice {

    private Office office;

    @Before
    public void setup(){
        office = new Office();
        office.setOfficeCode(25);
        office.setCity("Wolfenbuettel");
        office.setState("USA");
        office.setCountry("Deutschland");
        office.setTerritory("Heideland");
    }

    @Test
    public void exampleJSON(){
        try {
            JAXBContext context = JAXBContext.newInstance(Office.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(office, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping(){
        try {
            JAXBContext context = JAXBContext.newInstance(Office.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = File.createTempFile("test","office");
            marshaller.marshal(office, file);
            Office officeJSON = (Office) unmarshaller.unmarshal(file);
            Assert.assertEquals(office, officeJSON);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
