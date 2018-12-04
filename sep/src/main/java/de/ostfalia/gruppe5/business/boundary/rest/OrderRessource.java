package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.boundary.OrderDetailService;
import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.controller.IBANCalculator;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.OrderDetailsID;
import de.ostfalia.gruppe5.business.entity.Payment;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.flow.builder.ReturnBuilder;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@DeclareRoles({"EMPLOYEE", "CUSTOMER"})
@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/orders")
public class OrderRessource {

    public OrderRessource() {
    }

    @Inject
    private CustomerUser customerUser;

    @Inject
    private OrderService service;
    
    @Inject 
    private ProductService productService;

    @Inject
    private CustomerService customerService;

    @Inject
    private OrderDetailService orderDetailService;
    
    @Inject 
    private PaymentService paymentService;

    @Context
    private UriInfo uriinfo;

    @RolesAllowed({"EMPLOYEE", "CUSTOMER"})
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders() {
        Integer userId = customerUser.getId();
        if (userId == null)
            return service.findAll();
        else
            return service.getOrdersByCustomerId(userId);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("id") Integer id) {
        return service.find(id);
    }


    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postOrder(JsonObject json) {
        Order order = new Order();
        Integer orderNumber = service.nextID();
        order.setOrderNumber(orderNumber);
        populateOrder(json, order);
        service.update(order);
//        service.save(order);
//        Order parsed = service.find(order.getOrderNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(OrderRessource.class, "getOrder").build(order.getOrderNumber());
        return Response.created(uri).build();
    }
    
    @POST
    @Path("/basket/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postOrderBasket(@PathParam("id") Integer id, JsonObject json) {	
        Order order = new Order();
        Integer orderNumber = service.nextID();
        fillOrder(json, order, id, orderNumber);
        fillOrderDetails(json, orderNumber);
        fillPayment(json, orderNumber, id);
        
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(OrderRessource.class, "getOrder").build(order.getOrderNumber());
        return Response.created(uri).build();
    }
    
    @RolesAllowed("CUSTOMER")
    @POST
    @Path("/basket")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postOrderBasket(JsonObject json) {	
        Order order = new Order();
        Integer orderNumber = service.nextID();
        fillOrder(json, order, customerUser.getId(), orderNumber);
        fillOrderDetails(json, orderNumber);
        fillPayment(json, orderNumber, customerUser.getId());
        
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(OrderRessource.class, "getOrder").build(order.getOrderNumber());
        return Response.created(uri).build();
    }
    
    private void fillPayment(JsonObject json, Integer orderNumber, Integer customerNumber) {
    	String iban = calculateIBAN(json);
    	Payment payment = new Payment();
    	payment.setAmount(Double.parseDouble(json.getString("amount")));
    	payment.setCheckNumber(iban + "_" + orderNumber);
    	payment.setPaymentDate(LocalDate.now());
    	payment.setCustomerNumber(customerService.find(customerNumber));
    	paymentService.save(payment);
    }
    
    private String calculateIBAN(JsonObject json) {
    	if(json.getString("iban") != null || json.getString("iban") != "") {
    		return IBANCalculator.calculateDEIBANFromKntnrAndBlz(json.getString("kntnr"), json.getString("blz"));
    	} else {
    		return json.getString("iban");
    	}
    }
    
    private void fillOrderDetails(JsonObject json, Integer orderNumber) {
    	
    	json.getJsonArray("orders").stream().forEach(s -> {
    		String productCode = s.asJsonObject().getString("productCode");
    		OrderDetailsID id = new OrderDetailsID(orderNumber, productCode);
    		OrderDetail orderDetail = new OrderDetail();
    		orderDetail.setId(id);
    		orderDetail.setOrderLineNumber((short) 1);
    		Integer quantityOrdered = s.asJsonObject().getInt("amount");
    		orderDetail.setQuantityOrdered(quantityOrdered);
    		orderDetail.setPriceEach(productService.find(productCode).getBuyPrice());
    		orderDetail.setProductCode(productService.find(productCode));
    		orderDetail.setOrderNumber(service.find(orderNumber));
    		orderDetailService.save(orderDetail);
    		});
    	
    }
    
    private void fillOrder(JsonObject json, Order order, int customerNumber, Integer orderNumber) {
    	order.setOrderNumber(orderNumber);
    	order.setComments("A Comment");
    	order.setOrderDate(LocalDate.now());
    	order.setRequiredDate(LocalDate.now());
    	order.setShippedDate(LocalDate.now());
    	order.setStatus("In Process");
    	
        Customer customer = customerService.find(customerNumber);
        order.setCustomerNumber(customer);
        service.update(order);
    }

    private void populateOrder(JsonObject json, Order order) {
        order.setOrderDate(localDateFromJson(json.get("orderDate").asJsonObject()));
        order.setShippedDate(localDateFromJson(json.get("shippedDate").asJsonObject()));
        order.setRequiredDate(localDateFromJson(json.get("requiredDate").asJsonObject()));
        order.setStatus(json.getString("status"));
        order.setComments(json.get("comments").toString());

        int customerNumber = json.getInt("customerNumber");
        Customer customer = customerService.find(customerNumber);
        order.setCustomerNumber(customer);
    }

    private LocalDate localDateFromJson(JsonObject orderDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(orderDate.get("year"));
        sb.append(" ");
        String monthCaps = orderDate.getString("month");
        String monthLower = monthCaps.toLowerCase();
        String monthGood = monthLower.substring(0, 1).toUpperCase() + monthLower.substring(1);
        sb.append(monthGood);
        sb.append(" ");
        String day = orderDate.get("dayOfMonth").toString();
        if (day.length() < 2)
            sb.append("0");
        sb.append(day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(sb.toString(), formatter);
        return date;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putOrder(@PathParam("id") Integer id, JsonObject json) {
        Order order = service.find(id);
        Integer jsonId = json.getInt("orderNumber");
        if (!order.getOrderNumber().equals(jsonId)) {
            return Response.status(400).build();
        }
        populateOrder(json, order);

        service.update(order);

        GenericEntity<Order> entity = new GenericEntity<>(service.find(id), Order.class);
        return Response.ok().entity(entity).build();
    }

    /*
     * Delete nur für Produkte die noch nie bestellt wurden!.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProduct(@PathParam("id") Integer id) {
        Order order = service.find(id);
        if (order == null) {
            return Response.status(404).build();
        }
        service.deleteById(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/assignedCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getAssignedCustomer(@PathParam("id") Integer id) {
        Order order = service.find(id);
        return order.getCustomerNumber();
    }


    @GET
    @Path("/{id}/orderDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDetail> getOrderDetails(@PathParam("id") Integer id) {
        Order order = service.find(id);
        Integer orderNumber = order.getOrderNumber();
        return orderDetailService.getAllOrderDetails(orderNumber);
    }
}
