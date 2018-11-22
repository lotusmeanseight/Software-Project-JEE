package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Payment;

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

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/payments")
public class PaymentRessource {

    @Inject
    private PaymentService paymentService;
    @Inject
    private CustomerService customerService;
    @Context
    private UriInfo uriinfo;

    public PaymentRessource(){
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getPayments(){
        return paymentService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(@PathParam("id") String id){
        return paymentService.find(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postPayment(JsonObject jsonObject){
        Payment payment = new Payment();
        populatePayment(jsonObject, payment);
        paymentService.save(payment);
        Payment parsed = paymentService.find(payment.getCheckNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(PaymentRessource.class, "getPayment")
                .build(parsed.getCheckNumber());
        return Response.created(uri).build();
    }

    private void populatePayment(JsonObject jsonObject, Payment payment){
        payment.setPaymentDate(this.localDateFromJson(jsonObject.get("paymentDate").asJsonObject()));
        payment.setAmount(Double.parseDouble(jsonObject.get("amount").toString()));
        payment.setCustomerNumber((Customer) jsonObject.get("customerNumber"));
    }

    @PUT
    @Path("/{id}")
    public Response putPayment(@PathParam(("id")) String id, JsonObject jsonObject){
        Payment payment = paymentService.find(id);
        String jsonID = jsonObject.getString("checkNumber");
        if(!payment.getCheckNumber().equals(jsonID)){
            return Response.status(400).build();
        }
        populatePayment(jsonObject, payment);
        paymentService.update(payment);

        GenericEntity<Payment> entity = new GenericEntity<>
                (paymentService.find(id), Payment.class);
        return Response.ok().entity(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePayment(@PathParam("id") String id){
        Payment payment = paymentService.find(id);
        if(payment == null){
            return Response.status(404).build();
        }else{
            GenericEntity<Payment> entity =
                    new GenericEntity<>(payment, Payment.class);
            paymentService.deleteById(id);
            return Response.ok().entity(entity).build();
        }
    }

    @GET
    @Path("/{id}/assignedCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getPaymentAssignedCustomer(@PathParam("id") String id){
        System.out.println("ERROR with id "+id);
        List<Payment> payments = paymentService.findByCheckNumber(id);
        System.out.println("ERROR payment "+payments.size());
        payments.stream().forEach(System.out::println);
        System.out.println("ERROR payment "+payments.get(0));
        return payments.get(0).getCustomerNumber();
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
        sb.append(day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(sb.toString(), formatter);
        return date;
    }
}
