import java.time.LocalDateTime;

import de.ostfalia.gruppe5.models.implementations.CustomerImpl;
import de.ostfalia.gruppe5.models.implementations.EmployeeImpl;
import de.ostfalia.gruppe5.models.implementations.OfficeImpl;
import de.ostfalia.gruppe5.models.implementations.OrderDetailImpl;
import de.ostfalia.gruppe5.models.implementations.OrderImpl;
import de.ostfalia.gruppe5.models.implementations.PaymentImpl;
import de.ostfalia.gruppe5.models.implementations.ProductImpl;
import de.ostfalia.gruppe5.models.implementations.ProductLineImpl;

public class ExampleData {

	
	CustomerImpl customer = new CustomerImpl();
	EmployeeImpl employee = new EmployeeImpl();
	OfficeImpl office = new OfficeImpl();
	OrderDetailImpl orderDetail = new OrderDetailImpl();
	OrderImpl order = new OrderImpl();
	PaymentImpl payment = new PaymentImpl();
	ProductImpl product = new ProductImpl();
	ProductLineImpl productLine = new ProductLineImpl();
    
	public ExampleData() {
		//Customer
		customer.setAddressLine1("Hundestr.4");
	    customer.setAddressLine2("c");
	    customer.setCity("Braunschweig");
	    customer.setContactFirstName("Peter");
	    customer.setContactLastName("Zwegert");
	    customer.setCountry("Deutschland");
	    customer.setCreditLimit(1.0);
	    customer.setCustomerName("Peter Zwegert");
	    customer.setPhone("0123456789");
	    customer.setPostalCode("38388");
	    customer.setState("Niedersachsen");
		
		//Employee
		employee.setLastName("Gert");
		employee.setFirstName("Geralt");
		employee.setExtension("Testo");
		employee.setEmail("haggo5@testmail.fr");
		employee.setReportsTo(1);
		employee.setJobTitle("Arbeiter");
	    
		//Office
		office.setAddressLine1("Officestraße 5");
		office.setAddressLine2("a");
		office.setCity("Officestadt");
		office.setCountry("Deutschland");
		office.setPhone("12345678910");
		office.setPostalCode("38388");
		office.setState("Niedersachen");
		office.setTerritory("Hier");
		
		//OrderDetails
		
		orderDetail.setOrderLineNumber((short) 10);
		orderDetail.setPriceEach((double) 10);
		orderDetail.setProductCode(null);
		orderDetail.setQuantityOrdered(2);
		
		//Order
		
		order.setOrderDate(LocalDateTime.now());
		order.setRequiredDate(LocalDateTime.now());
		order.setRequiredDate(LocalDateTime.now());
		order.setShippedDate(LocalDateTime.now());
		order.setStatus("Liefern");
		order.setComments("Aufpassen zerbrechlich.");
		
		//Payment
		
		payment.setPaymentDate(LocalDateTime.now());
		payment.setAmount(2.0);
		
		//Product
		
		product.setProductName("Haus");
		product.setProductLine("Line Zwoelf");
		product.setProductScale("50cm");
		product.setProductVendor("Hellena");
		product.setProductDescription("Ein viereckiges Haus.");
		product.setQuantityInStock(12);
		product.setBuyPrice(1.60);
		product.setMSRP(1.0);
		
		//Product Line
		
		productLine.setTextDescription("ich habe keine Ahnung");
		productLine.setHtmlDescription(null);
		productLine.setBLOB(null);
	    
	    //Wenn alle Beispiele erstellt sind.
	    customer.setSalesRepEmployeeNumber(employee);
	    employee.setOfficeCode(office);
	    order.setCustomerNumber(customer);
	}
	
    
	
}
