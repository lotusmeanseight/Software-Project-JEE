package de.ostfalia.gruppe5;

import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@BasicAuthenticationMechanismDefinition
@ApplicationPath("api")
public class MyApplication extends Application {
    public MyApplication() {
    }
}
