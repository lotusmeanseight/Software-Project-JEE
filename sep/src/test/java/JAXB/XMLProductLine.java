package JAXB;

import de.ostfalia.gruppe5.business.entity.ProductLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class XMLProductLine {
    private ProductLine productLine;

    @Before
    public void setup(){
        productLine = new ProductLine();
        productLine.setProductLine("Motorcycles");
        productLine.setImage(null);
        productLine.setHtmlDescription("Lorem");
        productLine.setTextDescription("Ipsum");
    }

    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(ProductLine.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(productLine, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping(){
        try {
            JAXBContext context = JAXBContext.newInstance(ProductLine.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = File.createTempFile("test","productLine");
            marshaller.marshal(productLine, file);
            ProductLine productLineJSON = (ProductLine) unmarshaller.unmarshal(file);
            Assert.assertEquals(productLine, productLineJSON);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
