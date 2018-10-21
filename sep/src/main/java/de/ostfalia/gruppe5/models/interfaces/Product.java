package de.ostfalia.gruppe5.models.interfaces;

public interface Product {
    ProductLine getProductCode();

    void setProductCode(ProductLine productCode);

    String getProductName();

    void setProductName(String productName);

    String getProductLine();

    void setProductLine(String productLine);

    String getProductScale();

    void setProductScale(String productScale);

    String getProductVendor();

    void setProductVendor(String productVendor);

    String getProductDescription();

    void setProductDescription(String productDescription);

    Integer getQuantityInStock();

    void setQuantityInStock(Integer quantityInStock);

    Double getBuyPrice();

    void setBuyPrice(Double buyPrice);

    Double getMSRP();

    void setMSRP(Double mSRP);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
