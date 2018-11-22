package de.ostfalia.gruppe5.business.boundary;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBacking {
	
	@Inject
	SecurityContext securityContext;
	
	@Inject
	ExternalContext externalContext;
	
	public String redirect() throws IOException {
    	if(securityContext.isCallerInRole("EMPLOYEE")) {
    		externalContext.redirect(externalContext.getRequestContextPath() + "/app/employeeRole/customer/customers.jsf");
        } else {
        	externalContext.redirect(externalContext.getRequestContextPath() + "/app/customerRole/customersView.jsf");
        }
    	return null;
	}

}
