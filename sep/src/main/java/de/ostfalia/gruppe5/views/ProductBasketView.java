package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.*;
import de.ostfalia.gruppe5.business.entity.*;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sound.midi.SysexMessage;
import java.math.BigDecimal;

@Named
@RequestScoped
public class ProductBasketView {

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

    public ProductBasketView(){
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
}
