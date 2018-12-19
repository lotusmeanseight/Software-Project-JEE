package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
//@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
@Stateless
public class OrderService extends AbstractTableJPAService<Order> {
    String targetUrl = "http://localhost:8080/sep-gruppe-5/orders";

    public String call(String id, String httpMethod, JsonObject body){
        RestCaller rest = new RestCaller();
        return rest.callRest(this.targetUrl,id,httpMethod, body);
    }

    //@RolesAllowed("CUSTOMER")
    public Order find(final String id) {
        String response = this.call(id,RestCaller.GET, null);
        Order order = new Order();
        this.populateOrder(response, order);
        return order;
    }

    //@RolesAllowed("CUSTOMER")
    public Order find(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.GET, null);
        Order order = new Order();
        this.populateOrder(response, order);
        return order;
    }

    public List<Order> findAll() {
        String response = this.call(null,RestCaller.GET, null);
        Order order = new Order();
        return this.populateOrderList(response);
    }

    //@RolesAllowed("CUSTOMER")
    public void save(final Order entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
    }

    public void delete(final Order entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.DELETE, null);
    }

    public void deleteById(final String id) {
        String response = this.call(id,RestCaller.DELETE, null);
    }

    public void deleteById(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
    }

    //@RolesAllowed("CUSTOMER")
    public Order update(final Order entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
        Order order = new Order();
        this.populateOrder(response, order);
        return order;
    }

    @Inject
    private CustomerService customerService;


    private List<Order> populateOrderList(String response) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
        JsonArray array = jsonObject1.asJsonArray();
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Order order = new Order();
            this.populateOrder(array.get(i).asJsonObject(), order);
            list.add(order);
        }
        return list;
    }

    private void populateOrder(String json, Order order) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(json);
        this.populateOrder(jsonObject1,order);
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



    public OrderService() {
        setEntityClass(Order.class);
    }

    public List<Order> getOrdersByOrderId(Integer id) {
        return null;
    }

    //@RolesAllowed("CUSTOMER")
    public Integer nextID() {
        Integer lastID = this.getEntityManager().createQuery("select MAX(o.orderNumber) from Order o", Integer.class)
                .getSingleResult();

        return ++lastID;
    }

    public List<Order> getOrdersByCustomerId(Integer id) {
        return this.findAll();
    }
}
