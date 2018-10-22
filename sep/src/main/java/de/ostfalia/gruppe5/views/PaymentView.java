package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.models.Payment;
import de.ostfalia.gruppe5.services.CustomerService;
import de.ostfalia.gruppe5.services.PaymentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class PaymentView {

    private Payment payment;
    @Inject
    private PaymentService service;

    public PaymentView() {
        payment = new Payment();
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Payment> getPayments() {
        return service.getAllPayments();
    }

    public String save() {
        service.save(payment);
        return null;
    }

    public String update(Payment payment) {
        service.update(payment);
        return null;
    }

    public String delete(Payment payment) {
        service.deleteById(payment.getCheckNumber());
        return null;
    }
}
