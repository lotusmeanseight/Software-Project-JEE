package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.ProductBasket;
import de.ostfalia.gruppe5.business.entity.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequestScoped
public class ProductBasketView {

    @Inject
    private ProductBasket productBasket;

    public ProductBasketView(){
    }

    public String buy(Product product){
        productBasket.buyProduct(product);
        return null;
    }

    public String remove(int indexOfList){
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
}
