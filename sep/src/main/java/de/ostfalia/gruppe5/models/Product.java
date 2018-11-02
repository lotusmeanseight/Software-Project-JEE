package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @Size(max=15)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productCode;

    @OneToMany(mappedBy = "productCode")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @NotNull
    @Size(max=80)
    private String productName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productline")
    private ProductLine productLine;

    @NotNull
    @Size(max=10)
    private String productScale;

    @NotNull
    @Size(max=50)
    private String productVendor;

    @NotNull
    private String productDescription;

    @NotNull
    private Integer quantityInStock;

    @NotNull
    private Double buyPrice;

    @NotNull
    private Double MSRP;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine.setProductLine(productLine);
    }

    public String getProductScale() {
        return productScale;
    }

    public void setProductScale(String productScale) {
        this.productScale = productScale;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getMSRP() {
        return MSRP;
    }

    public void setMSRP(Double mSRP) {
        MSRP = mSRP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getProductCode(), product.getProductCode()) &&
                Objects.equals(getProductName(), product.getProductName()) &&
                Objects.equals(getProductLine(), product.getProductLine()) &&
                Objects.equals(getProductScale(), product.getProductScale()) &&
                Objects.equals(getProductVendor(), product.getProductVendor()) &&
                Objects.equals(getProductDescription(), product.getProductDescription()) &&
                Objects.equals(getQuantityInStock(), product.getQuantityInStock()) &&
                Objects.equals(getBuyPrice(), product.getBuyPrice()) &&
                Objects.equals(getMSRP(), product.getMSRP());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getProductName(), getProductLine(), getProductScale(), getProductVendor(), getProductDescription(), getQuantityInStock(), getBuyPrice(), getMSRP());
    }
}
