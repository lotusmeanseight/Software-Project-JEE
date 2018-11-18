package de.ostfalia.gruppe5.business.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScoped
public class CustomAuthenticationMechanism implements HttpAuthenticationMechanism {

	@Inject
	private IdentityStoreHandler identityStoreHandler;
	
	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		if(request.getHeader("SEP-Authorization") != null) {
			String data = request.getParameterNames().nextElement();
			
			String[] partsOfData = data.split(":");
			
			String name = partsOfData[0];
			String password = partsOfData[1];
			
			UsernamePasswordCredential credential = new UsernamePasswordCredential(name, password); 
			
			CredentialValidationResult result = identityStoreHandler.validate(credential);
			
			return httpMessageContext.notifyContainerAboutLogin(result);
			
		} else {
			try {
				if(request.authenticate(response)) {
					return AuthenticationStatus.SUCCESS;
				} else {
					return AuthenticationStatus.SEND_FAILURE;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}

}
