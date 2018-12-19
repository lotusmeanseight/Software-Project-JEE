package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class ProductLineService extends AbstractTableJPAService<ProductLine> {
	String targetUrl = null;

	public String call(String id, String httpMethod, JsonObject body){
		RestCaller rest = new RestCaller();
		return rest.callRest(this.targetUrl,id,httpMethod,body);
	}

	//@RolesAllowed("CUSTOMER")
	public ProductLine find(final String id) {
		String response = this.call(id,RestCaller.GET, null);
		ProductLine productLine = new ProductLine();
		this.populateProductLine(response, productLine);
		return productLine;
	}

	//@RolesAllowed("CUSTOMER")
	public ProductLine find(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.GET, null);
		ProductLine productLine = new ProductLine();
		this.populateProductLine(response, productLine);
		return productLine;
	}

	public List<ProductLine> findAll() {
		String response = this.call(null,RestCaller.GET, null);
		ProductLine productLine = new ProductLine();
		return this.populateProductLineList(response);
	}

	private List<ProductLine> populateProductLineList(String response) {
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
		JsonArray array = jsonObject1.asJsonArray();
		List<ProductLine> list = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			ProductLine productLine= new ProductLine();
			this.populateProductLine(array.get(i).asJsonObject(), productLine);
			list.add(productLine);
		}
		return list;
	}

	//@RolesAllowed("CUSTOMER")
	public void save(final ProductLine entity) {
		String response = this.call(String.valueOf(entity.getProductLine()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
	}

	public void delete(final ProductLine entity) {
		String response = this.call(String.valueOf(entity.getProductLine()),RestCaller.DELETE, null);
	}

	public void deleteById(final String id) {
		String response = this.call(id,RestCaller.DELETE, null);
	}

	public void deleteById(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
	}


	private void populateProductLine(String json, ProductLine productLine) {
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(json);
		this.populateProductLine(jsonObject1,productLine);
	}

	private void populateProductLine(JsonObject json, ProductLine productLine) {
		productLine.setHtmlDescription(json.get("htmlDescription").toString());
		productLine.setTextDescription(json.get("textDescription").toString());

		productLine.setImage(json.get("image").toString().getBytes());
	}


	//@RolesAllowed("CUSTOMER")
	public ProductLine update(final ProductLine entity) {
		String response = this.call(String.valueOf(entity.getProductLine()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
		ProductLine productLine = new ProductLine();
		this.populateProductLine(response, productLine);
		return productLine;
	}


	public ProductLineService() {
		setEntityClass(ProductLine.class);
	}
}
