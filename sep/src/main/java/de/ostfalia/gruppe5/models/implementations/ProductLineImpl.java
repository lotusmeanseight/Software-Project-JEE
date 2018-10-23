package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Product;
import de.ostfalia.gruppe5.models.interfaces.ProductLine;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "ProductLine")
@Table(name = "productlines")
public class ProductLineImpl implements ProductLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(max = 50)
	private String productLine;

    @OneToMany(mappedBy = "productCode")
    private List<Product> productList = new ArrayList<>();
	
	private String textDescription;
	
	private String htmlDescription;
	
	private Serializable BLOB;

	@Override
	public String getProductLine() {
		return productLine;
	}

	@Override
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	@Override
	public String getTextDescription() {
		return textDescription;
	}

	@Override
	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	@Override
	public String getHtmlDescription() {
		return htmlDescription;
	}

	@Override
	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

	@Override
	public Serializable getBLOB() {
		return BLOB;
	}

	@Override
	public void setBLOB(Serializable bLOB) {
		BLOB = bLOB;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductLineImpl that = (ProductLineImpl) o;
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
