package de.ostfalia.gruppe5.logic.identityStores;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import de.ostfalia.gruppe5.models.Employee;

import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class EmployeeIdentityStore implements IdentityStore {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

		Employee employee = entityManager.find(Employee.class, Integer.parseInt(login.getPasswordAsString()));
		
		if(employee == null) {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
		
		String lastName = employee.getLastName();

		if (login.getCaller().equals(lastName)) {
			return new CredentialValidationResult(lastName, new HashSet<>(Arrays.asList("EMPLOYEE")));
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}

	}

}
