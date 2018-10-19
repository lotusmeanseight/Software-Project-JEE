package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
	
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

    public ProductLine getProductCode() {
		return productCode;
	}

    public void setProductCode(ProductLine productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
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

	
	
}
