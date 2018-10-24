package de.ostfalia.gruppe5.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.models.Office;

@Stateless
public class OfficeService {


    @PersistenceContext(name = "simple")
    EntityManager entityManager;
    
   //for delete method
    @Inject 
	EmployeeService empService;

    public OfficeService() {

    }

    public void save(Office office) {
        entityManager.persist(office);
    }

    private Office findById(String id) {
        return entityManager.find(Office.class, id);
    }
    
//  public boolean canBeDeleted(String id){
//	   List<Employee> emps = empService.getAllEmployees();
//	   boolean isEmployeInOffice = false;
//	   for(Employee emp : emps){
//	     if(id = emp.getOfficeCode()){
//	        return false;
//	     }
//	   return true;
//	   }
//	   }

    public void deleteById(String id) {
    
    }

    public List<Office> getAllOffices() {
        return entityManager.createQuery("select o from Office o", Office.class).getResultList();
    }

    public Office update(Office office) {
    	Office detachedOffice = this.findById(office.getOfficeCode());
		   detachedOffice.setCity(office.getCity());
		   detachedOffice.setCity(office.getPhone());
		   detachedOffice.setCity(office.getAddressLine1());
		   detachedOffice.setCity(office.getAddressLine2());
		   detachedOffice.setCity(office.getState());
		   detachedOffice.setCity(office.getCountry());
		   detachedOffice.setCity(office.getPostalCode());
		   detachedOffice.setCity(office.getTerritory());
		   return detachedOffice;
    }
}


