package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Product;
import de.ostfalia.gruppe5.models.interfaces.ProductLine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductImpl implements Product {
	
	@Id
	@Size(max=15)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    private ProductLine productCode;

	@NotNull
	@Size(max=80)
	private String productName;

	@NotNull
	@Size(max=50)
	private String productLine;
	
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

    @Override
	public ProductLine getProductCode() {
		return productCode;
	}

    @Override
	public void setProductCode(ProductLine productCode) {
		this.productCode = productCode;
	}

	@Override
	public String getProductName() {
		return productName;
	}

	@Override
	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String getProductLine() {
		return productLine;
	}

	@Override
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	@Override
	public String getProductScale() {
		return productScale;
	}

	@Override
	public void setProductScale(String productScale) {
		this.productScale = productScale;
	}

	@Override
	public String getProductVendor() {
		return productVendor;
	}

	@Override
	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}

	@Override
	public String getProductDescription() {
		return productDescription;
	}

	@Override
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	@Override
	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	@Override
	public Double getBuyPrice() {
		return buyPrice;
	}

	@Override
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Override
	public Double getMSRP() {
		return MSRP;
	}

	@Override
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
