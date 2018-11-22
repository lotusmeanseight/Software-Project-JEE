package JAXB.XML;

import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class XMLOrderDetails {
    private OrderDetail orderDetail;

    @Before
    public void setup(){
        orderDetail = new OrderDetail();
        Order tempOrder = new Order();
        tempOrder.setOrderNumber(500);
        orderDetail.setOrderNumber(tempOrder);
        Product tempProduct = new Product();
        tempProduct.setProductCode("AS100");
        orderDetail.setProductCode(tempProduct);
        orderDetail.setOrderLineNumber((short) 10);
        orderDetail.setPriceEach(1.1d);
        orderDetail.setQuantityOrdered(100);
    }


    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(OrderDetail.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(orderDetail, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping(){
        try {
            JAXBContext context = JAXBContext.newInstance(OrderDetail.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = File.createTempFile("test","orderDetail");
            marshaller.marshal(orderDetail, file);
            OrderDetail orderDetailJSON = (OrderDetail) unmarshaller.unmarshal(file);
            Assert.assertEquals(orderDetail, orderDetailJSON);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
