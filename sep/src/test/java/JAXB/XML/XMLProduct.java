package JAXB.XML;

import de.ostfalia.gruppe5.business.entity.Office;
import de.ostfalia.gruppe5.business.entity.Product;
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

public class XMLProduct {

    private Product product;

    @Before
    public void setup(){
        product = new Product();
        product.setProductCode("AS110");
        product.setBuyPrice(1.11);
        product.setMSRP(2.22);
        product.setProductDescription(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. In lobortis urna eget diam egestas ullamcorper. Mauris a cursus mauris. Quisque at velit sem. Etiam dignissim libero in quam cursus euismod. Nullam fringilla laoreet leo at malesuada. Praesent pretium, leo eu aliquam dignissim, nulla nunc viverra tortor, non porttitor est enim quis magna. Donec et elementum mauris. Nulla est metus, sodales sed malesuada vel, condimentum eu mi. Quisque tempor malesuada ipsum id blandit. Vivamus eu euismod risus.\n" +
                "\n" +
                "Pellentesque magna magna, sollicitudin et augue sed, tincidunt feugiat velit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce sollicitudin faucibus velit eu congue. Proin a augue eu ligula porttitor semper. Nulla facilisi. Phasellus bibendum, sapien sed porttitor rutrum, orci justo ornare libero, id rhoncus justo sem in ligula. Suspendisse fringilla erat sit amet rutrum vestibulum. Quisque sed justo eu sem iaculis ullamcorper. Quisque eu aliquam tortor, dignissim viverra velit. Curabitur nec nisl sit amet nunc rhoncus aliquam a luctus tortor. Fusce ultrices tincidunt nunc quis molestie. Cras in faucibus orci.\n" +
                "\n" +
                "Cras auctor sollicitudin turpis, et pulvinar odio tincidunt sit amet. Nulla tincidunt nisl rutrum est gravida, ut congue orci tincidunt. Cras id luctus eros, id vehicula metus. Proin sit amet malesuada sapien, in hendrerit ligula. Maecenas at nisi sed nisl congue mollis. Donec et semper ex. Morbi tincidunt, augue et facilisis lacinia, odio nunc vehicula neque, ut maximus velit nisi ut arcu. Integer semper est et ligula dapibus tristique. Nulla tempus lobortis purus, eget scelerisque ex rutrum at. Morbi ac augue ac nisi pulvinar tempus vitae non augue. Praesent ornare euismod massa, sit amet posuere sapien placerat sed. Maecenas eu vehicula dui.");
        product.setProductName("Lorem");
        product.setProductVendor("Ipsum");
        ProductLine tempLine = new ProductLine();
        tempLine.setProductLine("Motorcycles");
        product.setProductLine(tempLine);
    }

    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(Product.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(product, System.out);
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
            File file = File.createTempFile("test","product");
            marshaller.marshal(product, file);
            Product productJSON = (Product) unmarshaller.unmarshal(file);
            Assert.assertEquals(product, productJSON);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
