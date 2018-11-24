package de.ostfalia.gruppe5.business.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NamedQueries({ @NamedQuery(name = "ProductLine.countAll", query = "SELECT COUNT(p) FROM ProductLine p"),
		@NamedQuery(name = "ProductLine.findAll", query = "SELECT p FROM ProductLine p") })
@Entity
@Table(name = "productlines")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductLine implements Serializable {

	@Id
	@Size(max = 50)
	private String productLine;

	@OneToMany(mappedBy = "productLine")
	private List<Product> productList = new ArrayList<>();

	private String textDescription;

	private String htmlDescription;

	@Basic
	private byte[] image;

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProductLine that = (ProductLine) o;
		return Objects.equals(getProductLine(), that.getProductLine()) && Objects.equals(productList, that.productList)
				&& Objects.equals(getTextDescription(), that.getTextDescription())
				&& Objects.equals(getHtmlDescription(), that.getHtmlDescription())
				&& Objects.equals(getImage(), that.getImage());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProductLine(), productList, getTextDescription(), getHtmlDescription(), getImage());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
			try {
				sb.append("[");
				sb.append(field.getName());
				sb.append("=");
				sb.append(field.get(this));
				sb.append("] ");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return sb.toString();
	}
}
