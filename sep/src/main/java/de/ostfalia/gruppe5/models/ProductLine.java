package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "productlines")
public class ProductLine implements Serializable {

	@Id
	@Size(max=50)
	@ManyToOne
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Product productLine;
	
	private String textDescription;
	
	private String htmlDescription;
	
	private Serializable BLOB;

    public Product getProductLine() {
		return productLine;
	}

    public void setProductLine(Product productLine) {
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
