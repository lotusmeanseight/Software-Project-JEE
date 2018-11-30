package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Item;
import de.ostfalia.gruppe5.business.entity.Product;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProductBasket implements Serializable {

    private final List<Item> itemList = new ArrayList<>();

    public ProductBasket(){
    }

    /**
     * Changes quantity of basket to the new quantity
     * if newquantity is 0 then product is deleted
     * if newquantity is below 0 then a validatorexception with a facesmessage is thrown
     * else the item in the basket is set to the newQuantity
     * @param indexOfList position of the item in the basket
     * @param newQuantity quantity of the product to buy
     */
    public void changeQuantityInBasket(int indexOfList, int newQuantity){
        if(newQuantity < 0){
            List<FacesMessage> messages = new ArrayList<>();
            messages.add(new FacesMessage("Die Anzahl ist zu niedrig für: "
                    + getItemList().get(indexOfList).getProduct().getProductName()));
            throw new ValidatorException(messages);
        }else if(newQuantity == 0){
            removeItem(indexOfList);
        }else{
            getItemList().get(indexOfList).setQuantity(newQuantity);
        }
    }

    /**
     * remove item from basket
     * @param indexOfList position of item in basket
     */
    public void removeItem(int indexOfList){

        getItemList().remove(indexOfList);
    }

    /**
     * Addding Product to Basket, if it is already inside then add one.
     * @param product
     */
    public void buyProduct(Product product){
        int basketPosition = checkBasket(product);
        if(!(basketPosition >= 0)){
            getItemList().add(new Item(product, 1));
        }else{
            getItemList().get(basketPosition).setQuantity(getItemList().get(basketPosition).getQuantity()+1);
        }
    }

    /**
     * calculate total price of items in basket
     * @return total money to pay in transaction
     */
    public BigDecimal calulateTotal(){
        double total = 0;
        for(Item item : getItemList()){
            total = total + item.getProduct().getBuyPrice() * item.getQuantity();
        }
        return BigDecimal.valueOf(total);
    }

    /**
     * Checks if Product is in Basket, if it is returns the index of the itemlist, else
     * returns -1 for product not found in basket.
     * @param product the new product to be added to the basket
     * @return index of the product if it is present in the basket.
     */
    private int checkBasket(Product product){
        for (int i = 0; i < getItemList().size(); i++) {
            if(getItemList().get(i).getProduct().getProductCode().equals(product.getProductCode())){
                return i;
            }
        }

        return -1;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
