package de.ostfalia.gruppe5.business.controller;

import static java.lang.String.format;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScoped
public class CustomAuthenticationMechanism implements HttpAuthenticationMechanism {

	@Inject
	private IdentityStoreHandler identityStoreHandler;

	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {

		if (request.getHeader("SEP-Authorization") != null) {
			String[] credentials = getSepCredentials(request);

			String name = credentials[0];
			String password = credentials[1];

			UsernamePasswordCredential credential = new UsernamePasswordCredential(name, password);
			CredentialValidationResult result = identityStoreHandler.validate(credential);

			return httpMessageContext.notifyContainerAboutLogin(result);

		}

		if (request.getHeader("Authorization") != null) {
			String[] credentials = getAutCredentials(request);

			return httpMessageContext.notifyContainerAboutLogin(createResult(request, credentials));

		}

		if (httpMessageContext.isProtected()) {
			response.setHeader("WWW-Authenticate", format("Basic realm=\"%s\"", "Test"));
			return httpMessageContext.responseUnauthorized();
		}

		return httpMessageContext.doNothing();

	}

	private String[] getAutCredentials(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
			return new String(parseBase64Binary(authorizationHeader.substring(6))).split(":");
		}

		return null;
	}
	
	private String[] getSepCredentials(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("SEP-Authorization");
		if (authorizationHeader != null) {
			return new String(authorizationHeader).split(":");
		}

		return null;
	}
	
	private CredentialValidationResult createResult(HttpServletRequest request, String[] credentials) {
		String name = credentials[0];
		String password = credentials[1];

		UsernamePasswordCredential credential = new UsernamePasswordCredential(name, password);
		return identityStoreHandler.validate(credential);
		
	}

}
