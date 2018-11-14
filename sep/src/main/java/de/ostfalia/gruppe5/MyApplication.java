package de.ostfalia.gruppe5;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationScoped
@ApplicationPath("/api")
@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@BasicAuthenticationMechanismDefinition
@FacesConfig
public class MyApplication extends Application {
    public MyApplication() {
    }
}
