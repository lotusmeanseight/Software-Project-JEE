package de.ostfalia.gruppe5.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productlines")
public class ProductLine {

	@Id
	@Size(max=50)
	@ManyToOne
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String productLine;
	
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
