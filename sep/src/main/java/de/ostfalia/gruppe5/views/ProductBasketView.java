package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.*;
import de.ostfalia.gruppe5.business.entity.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sound.midi.SysexMessage;
import java.math.BigDecimal;
import java.time.LocalDate;

@Named
@RequestScoped
public class ProductBasketView {

    @Inject
    private CustomerUser customerUser;

    private HtmlDataTable datatable;
    @Inject
    private ProductBasket productBasket;
    @Inject
    private CustomerService customerService;
    @Inject
    private OrderService orderService;
    @Inject
    private OrderDetailService orderDetailService;
    @Inject
    private PaymentService paymentService;

    private Customer customer;
    private Order order;
    private OrderDetail orderDetail;
    private Payment payment;
    private BigDecimal totalPrice;

    private LocalDate currentDate;

    private String iban;
    private Integer accountNumber;
    private Integer blz;

    public ProductBasketView(){
    }

    @PostConstruct
    private void init(){
        currentDate = LocalDate.now();
    }

    public void processOrder(){

    }

    private Order createOrder(){
        Order order = new Order();
        order.setStatus((OrderStatus.IN_PROCESS.toString()));
        order.setOrderDate(currentDate);
        order.setRequiredDate(currentDate);
        order.setShippedDate(currentDate);

        return order;
    }

    private Payment createPayment() {
        Payment payment = new Payment();
        payment.setPaymentDate(currentDate);

        return  payment;
    }

    public String buy(Product product , int quantity){
        productBasket.buyProduct(product, quantity);
        return null;
    }

    public String remove(){
        int indexOfList = datatable.getRowIndex();
        productBasket.removeItem(indexOfList);
        return null;
    }

    public String changeQuantity(int indexOfList, int newQuantity){
        productBasket.changeQuantityInBasket(indexOfList, newQuantity);
        return null;
    }

    public boolean isCustomerUser(){
        return customerUser.getId() != null;
    }

    public Customer getCustomer(int customerNumber){
        return customerService.find(customerNumber);
    }

    public BigDecimal getTotalPrice(){
        return productBasket.calulateTotal();
    }

    public ProductBasket getProductBasket() {
        return productBasket;
    }

    public void setProductBasket(ProductBasket productBasket) {
        this.productBasket = productBasket;
    }

    public HtmlDataTable getDatatable() {
        return datatable;
    }

    public void setDatatable(HtmlDataTable datatable) {
        this.datatable = datatable;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getBlz() {
        return blz;
    }

    public void setBlz(Integer blz) {
        this.blz = blz;
    }
}
