package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "productlines")
public class ProductLine implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(max = 50)
	private String productLine;

	@OneToMany(mappedBy = "productCode")
	private List<Product> productList = new ArrayList<>();

	private String textDescription;
	
	private String htmlDescription;
	
	private byte [] BLOB;


	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

	public byte[] getBLOB() {
		return BLOB;
	}

	public void setBLOB(byte[] BLOB) {
		this.BLOB = BLOB;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductLine that = (ProductLine) o;
		return Objects.equals(getProductLine(), that.getProductLine()) &&
				Objects.equals(productList, that.productList) &&
				Objects.equals(getTextDescription(), that.getTextDescription()) &&
				Objects.equals(getHtmlDescription(), that.getHtmlDescription()) &&
				Objects.equals(getBLOB(), that.getBLOB());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProductLine(), productList, getTextDescription(), getHtmlDescription(), getBLOB());
	}
}
