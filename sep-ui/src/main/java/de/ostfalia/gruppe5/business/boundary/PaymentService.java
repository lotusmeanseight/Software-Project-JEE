package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Payment;

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
public class PaymentService extends AbstractTableJPAService<Payment> {
    String targetUrl = "http://localhost:8080/sep-gruppe-5/payments";

    public String call(String id, String httpMethod, JsonObject body){
        RestCaller rest = new RestCaller();
        return rest.callRest(this.targetUrl,id,httpMethod,body);
    }

    //@RolesAllowed("CUSTOMER")
    public Payment find(final String id) {
        String response = this.call(id,RestCaller.GET, null);
        Payment  payment = new Payment();
        this.populatePayment(response, payment);
        return payment;
    }

    //@RolesAllowed("CUSTOMER")
    public Payment find(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.GET, null);
        Payment  payment = new Payment();
        this.populatePayment(response, payment);
        return payment;
    }

    public List<Payment> findAll() {
        String response = this.call(null,RestCaller.GET, null);
        Payment  payment = new Payment();
        return this.populatePaymentList(response);
    }

    //@RolesAllowed("CUSTOMER")
    public void save(final Payment entity) {
        String response = this.call(String.valueOf(entity.getCheckNumber()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
    }

    public void delete(final Payment entity) {
        String response = this.call(String.valueOf(entity.getCheckNumber()),RestCaller.DELETE, null);
    }

    public void deleteById(final String id) {
        String response = this.call(id,RestCaller.DELETE, null);
    }

    public void deleteById(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
    }

    //@RolesAllowed("CUSTOMER")
    public Payment update(final Payment entity) {
        String response = this.call(String.valueOf(entity.getCheckNumber()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
        Payment  payment = new Payment();
        this.populatePayment(response, payment);
        return payment;
    }

    @Inject
    private CustomerService customerService;


    private List<Payment> populatePaymentList(String response) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
        JsonArray array = jsonObject1.asJsonArray();
        List<Payment> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Payment payment = new Payment();
            this.populatePayment(array.get(i).asJsonObject(), payment);
            list.add(payment);
        }
        return list;
    }

    private void populatePayment(String jsonObject, Payment payment) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(jsonObject);
        this.populatePayment(jsonObject1,payment);
    }

    private void populatePayment(JsonObject jsonObject, Payment payment) {
        JsonObject customerNumberJson = jsonObject.getJsonObject("customerNumber");
        Integer customerNumberString = customerNumberJson.getInt("customerNumber");
        Customer customer = this.customerService.find(customerNumberString);
        payment.setCustomerNumber(customer);
        payment.setPaymentDate(this.localDateFromJson(jsonObject.get("paymentDate").asJsonObject()));
        payment.setAmount(Double.parseDouble(jsonObject.get("amount").toString()));

        payment.setCheckNumber(customer.getCustomerNumber()+"_"+System.nanoTime());
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


    public List<Payment> findByCheckNumber(String checknumber) {
        return null;
    }

    public PaymentService() {
        setEntityClass(Payment.class);
    }
}
