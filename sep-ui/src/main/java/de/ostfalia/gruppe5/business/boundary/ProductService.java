package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

//@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
@Stateless
public class ProductService extends AbstractTableJPAService<Product> {
	String targetUrl = null;



	public String call(String id, String httpMethod, JsonObject body){
		RestCaller rest = new RestCaller();
		return rest.callRest(this.targetUrl,id,httpMethod,body);
	}

	//@RolesAllowed("CUSTOMER")
	public Product find(final String id) {
		String response = this.call(id,RestCaller.GET, null);
		Product product = new Product();
		this.populateProduct(response, product);
		return product;
	}

	//@RolesAllowed("CUSTOMER")
	public Product find(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.GET, null);
		Product product = new Product();
		this.populateProduct(response, product);
		return product;
	}

	public List<Product> findAll() {
		String response = this.call(null,RestCaller.GET, null);
		Product product = new Product();
		return this.populateProductList(response);
	}

	private List<Product> populateProductList(String response) {
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
		JsonArray array = jsonObject1.asJsonArray();
		List<Product> list = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			Product product= new Product();
			this.populateProduct(array.get(i).asJsonObject(), product);
			list.add(product);
		}
		return list;
	}

	//@RolesAllowed("CUSTOMER")
	public void save(final Product entity) {
		String response = this.call(String.valueOf(entity.getProductCode()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
	}

	public void delete(final Product entity) {
		String response = this.call(String.valueOf(entity.getProductCode()),RestCaller.DELETE, null);
	}

	public void deleteById(final String id) {
		String response = this.call(id,RestCaller.DELETE, null);
	}

	public void deleteById(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
	}

	@Inject
	private ProductLineService productLineService;

	private void populateProduct(String json, Product product) {
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(json);
		this.populateProduct(jsonObject1,product);
	}
	private void populateProduct(JsonObject json, Product product) {
		product.setProductDescription(json.getString("productDescription"));
		product.setProductVendor(json.getString("productVendor"));
		product.setProductName(json.getString("productName"));
		product.setQuantityInStock(json.getInt("quantityInStock"));
		product.setBuyPrice(json.getJsonNumber("buyPrice").doubleValue());
		product.setMSRP(json.getJsonNumber("msrp").doubleValue());
		product.setProductScale(json.getString("productScale"));

		JsonObject productLineJson = json.getJsonObject("productLine");
		String productLineId = productLineJson.getString("productLine");
		ProductLine productLine = this.productLineService.find(productLineId);
		product.setProductLine(productLine);
	}


	//@RolesAllowed("CUSTOMER")
	public Product update(final Product entity) {
		String response = this.call(String.valueOf(entity.getProductCode()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
		Product product = new Product();
		this.populateProduct(response, product);
		return product;
	}


	public ProductService() {
		setEntityClass(Product.class);
	}

}
