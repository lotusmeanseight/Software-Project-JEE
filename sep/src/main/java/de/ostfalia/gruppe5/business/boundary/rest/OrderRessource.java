package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.OrderDetailService;
import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.business.entity.OrderDetail;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
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
    private OrderDetailService orderDetailService;

    @Context
    private UriInfo uriinfo;

    @RolesAllowed("CUSTOMER")
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrdersCustomer() {
        Integer userId = customerUser.getId();
        return service.getOrdersByCustomerId(userId);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders() {
        return service.findAll();
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
        order.setOrderNumber(service.nextID());
        populateOrder(json, order);
        service.save(order);
        Order parsed = service.find(order.getOrderNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(OrderRessource.class, "getOrder").build(parsed.getOrderNumber());
        return Response.created(uri).build();
    }

    private void populateOrder(JsonObject json, Order order) {
        System.out.println("ERROR json:" + json);
        LocalDate localDate = this.localDateFromJson(json.get("orderDate").asJsonObject());
        order.setOrderDate(localDateFromJson(json.get("orderDate").asJsonObject()));
        order.setShippedDate(localDateFromJson(json.get("shippedDate").asJsonObject()));
        order.setRequiredDate(localDateFromJson(json.get("requiredDate").asJsonObject()));

        order.setStatus(json.getString("status"));
        order.setComments(json.getString("comments"));
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
        if (day.length()<2)
            sb.append("0");
        System.out.println(day);
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
