package de.ostfalia.gruppe5.business.boundary;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBacking {

    @NotEmpty
    private String password;

    @NotEmpty
    private String lastName;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    public void submit() throws IOException {
            switch (continueAuthentication()) {

                case SEND_CONTINUE:
                    facesContext.responseComplete();
                    break;
                case SEND_FAILURE:
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                    break;
                case SUCCESS:
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeed", null));
                    if(securityContext.isCallerInRole("EMPLOYEE")) {
                    	externalContext.redirect(externalContext.getRequestContextPath() + "/app/employeeRole/customer/customers.jsf");
                    } else {
                    	externalContext.redirect(externalContext.getRequestContextPath() + "/app/customerRole/customersView.jsf");
                    }
                    
                    break;
                case NOT_DONE:
            }
    }

    public AuthenticationStatus continueAuthentication() {

        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(lastName, password))
        );
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    
}