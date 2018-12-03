package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.*;
import de.ostfalia.gruppe5.business.boundary.validation.IBAN;
import de.ostfalia.gruppe5.business.entity.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private Order order;
    private OrderDetail orderDetail;
    private Payment payment;
    private LocalDate currentDate;

    @IBAN
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
        this.order = createOrder();
        this.payment = createPayment();

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Item i : productBasket.getItemList()) {
            OrderDetail singleOrderDetail = new OrderDetail();
            singleOrderDetail.setOrderNumber(order);
            singleOrderDetail.setProductCode(i.getProduct());
            singleOrderDetail.setPriceEach(i.getProduct().getBuyPrice());
            singleOrderDetail.setQuantityOrdered(i.getQuantity());
            orderDetails.add(singleOrderDetail);
            this.orderDetailService.save(singleOrderDetail);
        }

        this.orderService.save(order);
        this.paymentService.save(payment);
    }

    private Order createOrder(){
        Order order = new Order();
        order.setOrderNumber(orderService.nextID());
        order.setOrderDate(currentDate);
        order.setRequiredDate(currentDate);
        order.setStatus(OrderStatus.IN_PROCESS.toString());
        order.setCustomerNumber(this.productBasket.getCustomer());

        return order;
    }

    private Payment createPayment() {
        Payment payment = new Payment();
        payment.setAmount(getTotalPrice());
        payment.setCustomerNumber(this.productBasket.getCustomer());
        payment.setCheckNumber(iban+"_"+order.getOrderNumber());
        payment.setPaymentDate(currentDate);

        return payment;
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

    public String changeQuantity(int newQuantity){
        int indexOfList = datatable.getRowIndex();
        productBasket.changeQuantityInBasket(indexOfList, newQuantity);
        return null;
    }

    public boolean isCustomerUser(){
        return customerUser.getId() != null;
    }

    public double getTotalPrice(){
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
