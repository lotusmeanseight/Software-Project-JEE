package de.ostfalia.gruppe5.logic.identityStores;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class EmployeeIdentityStore implements IdentityStore {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

		String lastName = entityManager.createQuery(
				"select p.LASTNAME from EMPLOYEES where p.EMPLOYEENUMBER is " + login.getPasswordAsString(),
				String.class).getResultList().get(0);

		if (login.getCaller().equals(lastName)) {
			return new CredentialValidationResult(lastName, new HashSet<>(Arrays.asList("EMPLOYEE")));
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}

	}

}
