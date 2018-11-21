package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.Order;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed("EMPLOYEE")
@Stateless
@Path("orders")
public class OrderRessource {

    public OrderRessource(){
    }

    @Inject
    private CustomerUser customerUser;

    @Inject
    private OrderService service;

    @Context
    private UriInfo uriinfo;

    @RolesAllowed("CUSTOMER")
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrdersCustomer(){
        Integer userId = customerUser.getId();
        return service.getOrdersByCustomerId(userId);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("id") Integer id){
        return service.find(id);
    }



    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postOrder(JsonObject json) {
        System.out.println("############################################### POST");
        System.out.println(json);
        System.out.println("############# Product");
        Order order = new Order();
        order.setOrderNumber(service.nextID());
        populateOrder(json, order);
        service.save(order);
        Order parsed = service.find(order.getOrderNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(ProductRessource.class, "getOrder").build(parsed.getOrderNumber());
        return Response.created(uri).build();
    }

    private void populateOrder(JsonObject json, Order order) {
        order.setOrderDate(LocalDate.parse(json.getString("orderDate")));
        order.setShippedDate(LocalDate.parse(json.getString("shippedDate")));
        order.setRequiredDate(LocalDate.parse(json.getString("requiredDate")));

        order.setStatus(json.getString("status"));
        order.setComments(json.getString("comments"));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putOrder(@PathParam("id") String id, JsonObject json) {
        System.out.println("############################################### PUT");
        Order order = service.find(id);
        String jsonId = json.getString("orderNumber");
        if (!order.getOrderNumber().equals(jsonId)) {
            System.out.println("################ "+id+" not same as "+jsonId);
            return Response.status(400).build();
        }
        populateOrder(json,order);

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
    public Response deleteProduct(@PathParam("id") String id) {
        System.out.println("############################################### DELETE");
        Order order = service.find(id);
        if (order == null) {
            return Response.status(404).build();
        }
        GenericEntity<Order> entity = new GenericEntity<>(order, Order.class);
        //TODO detached entity
        service.deleteById(id);
        return Response.ok().build();
    }
}
