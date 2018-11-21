package JAXB.XML;

import de.ostfalia.gruppe5.business.entity.Employee;
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

public class XMLEmployee {
    private Employee employee;

    @Before
    public void setup(){
        employee = new Employee();
        Office tempOffice = new Office();
        tempOffice.setOfficeCode(200);
        employee.setOfficeCode(tempOffice);
        employee.setEmployeeNumber(5);
        employee.setFirstName("Jeff");
        employee.setLastName("Wombo");
        employee.setEmail("test@test.de");
        employee.setJobTitle("CEO");
        employee.setExtension("X030");
        employee.setReportsTo(null);
    }

    @Test
    public void exampleXML(){
        try {
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(employee, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasValidMapping(){
        try{
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = File.createTempFile("test","employee");
            marshaller.marshal(employee, file);
            Employee employeeJSON = (Employee) unmarshaller.unmarshal(file);
            Assert.assertEquals(employee, employeeJSON);
        }catch (JAXBException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
