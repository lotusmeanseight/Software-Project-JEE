package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	private Serializable BLOB;

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

	public Serializable getBLOB() {
		return BLOB;
	}

	public void setBLOB(Serializable bLOB) {
		BLOB = bLOB;
	}
	
	
	
}
