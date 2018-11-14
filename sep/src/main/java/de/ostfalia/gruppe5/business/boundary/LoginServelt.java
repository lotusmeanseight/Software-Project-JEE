package de.ostfalia.gruppe5.business.boundary;

import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@BasicAuthenticationMechanismDefinition(realmName="${'peter-realm'}")
@WebServlet("/login")
public class LoginServelt extends HttpServlet {

}
