package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.OrderDetail;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

//@DeclareRoles({ "CUSTOMER", "EMPLOYEE" })
//@RolesAllowed({ "CUSTOMER", "EMPLOYEE" })
@Stateless
public class OrderDetailService extends AbstractJPAService<OrderDetail> {

    String targetUrl = null;

    public String call(String id, String httpMethod, JsonObject body){
        RestCaller rest = new RestCaller();
        return rest.callRest(this.targetUrl,id,httpMethod,body);
    }

    //@RolesAllowed("CUSTOMER")
    public OrderDetail find(final String id) {
        String response = this.call(id,RestCaller.GET, null);
        //TODO parse into Entity
        return null;
    }

    //@RolesAllowed("CUSTOMER")
    public OrderDetail find(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.GET, null);
        //TODO parse into Entity
        return null;
    }

    public List<OrderDetail> findAll() {
        String response = this.call(null,RestCaller.GET, null);
        //TODO parse into Entity List
        return null;
    }

    //@RolesAllowed("CUSTOMER")
    public void save(final OrderDetail entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
    }

    public void delete(final OrderDetail entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.DELETE, null);
    }

    public void deleteById(final String id) {
        String response = this.call(id,RestCaller.DELETE, null);
    }

    public void deleteById(final Integer id) {
        String response = this.call(String.valueOf(id),RestCaller.DELETE, null);
    }

    //@RolesAllowed("CUSTOMER")
    public OrderDetail update(final OrderDetail entity) {
        String response = this.call(String.valueOf(entity.getOrderNumber()),RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
        //TODO parse into Entity List
        return null;
    }



    public OrderDetailService() {
        setEntityClass(OrderDetail.class);
    }

    //@RolesAllowed({ "CUSTOMER", "EMPLOYEE" })
    public List<OrderDetail> getAllOrderDetails(Integer orderNumber) {
        return null;
    }
}