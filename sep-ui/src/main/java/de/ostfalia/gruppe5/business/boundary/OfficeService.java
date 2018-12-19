package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Office;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class OfficeService extends AbstractTableJPAService<Office> {

	String targetUrl = "http://localhost:8080/sep-gruppe-5/offices";

	public String call(String id, String httpMethod, JsonObject body){
		RestCaller rest = new RestCaller();
		return rest.callRest(this.targetUrl,id,httpMethod,body);
	}

	//@RolesAllowed("CUSTOMER")
	public Office find(final String id) {
		String response = this.call(id,RestCaller.GET, null);
		Office office = new Office();
		this.populateOffice(response, office);
		return office;
	}

	//@RolesAllowed("CUSTOMER")
	public Office find(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.GET, null);
		Office office = new Office();
		this.populateOffice(response, office);
		return office;
	}

	public List<Office> findAll() {
		String response = this.call(null,RestCaller.GET, null);
		return this.populateOfficeList(response);
	}

	//@RolesAllowed("CUSTOMER")
	public void save(final Office entity) {
		String response = this.call(String.valueOf(entity.getOfficeCode()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
	}

	public void delete(final Office entity) {
		String response = this.call(String.valueOf(entity.getOfficeCode()),RestCaller.DELETE, null);
	}

	public void deleteById(final String id) {
		String response = this.call(id,RestCaller.DELETE, null);
	}

	public void deleteById(final Integer id) {
		String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
	}

	//@RolesAllowed("CUSTOMER")
	public Office update(final Office entity) {
		String response = this.call(String.valueOf(entity.getOfficeCode()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
		Office office = new Office();
		this.populateOffice(response, office);
		return office;
	}

	private List<Office> populateOfficeList(String response) {
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
		JsonArray array = jsonObject1.asJsonArray();
		List<Office> list = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			Office office = new Office();
			this.populateOffice(array.get(i).asJsonObject(), office);
			list.add(office);
		}
		return list;
	}

	private void populateOffice(String json, Office office){
		JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(json);
		this.populateOffice(jsonObject1,office);
	}
	private void populateOffice(JsonObject json, Office office) {
		office.setCity(json.getString("city"));
		office.setPhone(json.getString("phone"));
		office.setAddressLine1(json.getString("addressLine1"));
		office.setAddressLine2(json.getString("addressLine2"));
		office.setState(json.getString("state"));
		office.setCountry(json.getString("country"));
		office.setPostalCode(json.get("postalCode").toString());
		office.setTerritory(json.getString("territory"));
	}


	public OfficeService() {
		setEntityClass(Office.class);
	}

}
