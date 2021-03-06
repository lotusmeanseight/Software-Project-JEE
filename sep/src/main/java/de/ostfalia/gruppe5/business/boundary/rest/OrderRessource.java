package de.ostfalia.gruppe5.business.boundary.rest;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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
import de.ostfalia.gruppe5.business.entity.Payment;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
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

	@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
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
		Response res = checkBasketJson(json);
		if (res != null) {
			return res;
		}
		Order order = new Order();
		Integer orderNumber = service.nextID();
		fillOrder(order, id, orderNumber);
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
		Response res = checkBasketJson(json);
		if (res != null) {
			return res;
		}
		Order order = new Order();
		Integer orderNumber = service.nextID();
		fillOrder(order, customerUser.getId(), orderNumber);
		fillOrderDetails(json, orderNumber);
		fillPayment(json, orderNumber, customerUser.getId());

		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(OrderRessource.class, "getOrder").build(order.getOrderNumber());
		return Response.created(uri).build();
	}

	private Response checkBasketJson(JsonObject json) {
		if (!json.containsKey("iban") || json.getString("iban") == null || json.getString("iban") == "") {
			if (!json.containsKey("kntnr") || !json.containsKey("blz") || json.getString("kntnr") == null
					|| json.getString("blz") == null || json.getString("kntnr") == "" || json.getString("blz") == "") {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}

		if (!json.containsKey("orders")) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			for (JsonValue s : json.getJsonArray("orders")) {
				if (!s.asJsonObject().containsKey("productCode") || !s.asJsonObject().containsKey("amount")) {
					return Response.status(Status.BAD_REQUEST).build();
				}
			}
		}

		return null;
	}

	private void fillPayment(JsonObject json, Integer orderNumber, Integer customerNumber) {
		String iban = calculateIBAN(json);
		Payment payment = new Payment();
		payment.setAmount((double) 1);
		payment.setAmount(calculateAmount(json));
		payment.setCheckNumber(iban + "_" + orderNumber);
		payment.setPaymentDate(LocalDate.now());
		payment.setCustomerNumber(customerService.find(customerNumber));
		paymentService.save(payment);
	}

	private Double calculateAmount(JsonObject json) {
		Double amount = 0.0;
		for (JsonValue s : json.getJsonArray("orders")) {
			String productCode = s.asJsonObject().getString("productCode");
			amount = +productService.find(productCode).getBuyPrice();
		}
		return amount;
	}

	private String calculateIBAN(JsonObject json) {

		if (!json.containsKey("iban") || json.getString("iban") == null || json.getString("iban") == "") {
			return IBANCalculator.calculateDEIBANFromKntnrAndBlz(json.getString("kntnr"), json.getString("blz"));
		} else {
			return json.getString("iban");
		}
	}

	private void fillOrderDetails(JsonObject json, Integer orderNumber) {

		json.getJsonArray("orders").stream().forEach(s -> {
			String productCode = s.asJsonObject().getString("productCode");
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderLineNumber((short) 1);
			Integer quantityOrdered = s.asJsonObject().getInt("amount");
			orderDetail.setQuantityOrdered(quantityOrdered);
			orderDetail.setPriceEach(productService.find(productCode).getBuyPrice());
			orderDetail.setProductCode(productService.find(productCode));
			orderDetail.setOrderNumber(service.find(orderNumber));
			orderDetailService.save(orderDetail);
		});

	}

	private void fillOrder(Order order, int customerNumber, Integer orderNumber) {
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
