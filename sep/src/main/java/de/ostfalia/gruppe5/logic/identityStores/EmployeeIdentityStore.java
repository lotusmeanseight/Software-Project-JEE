package de.ostfalia.gruppe5.logic.identityStores;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import de.ostfalia.gruppe5.models.Employee;
import de.ostfalia.gruppe5.userInformation.EmployeeUser;

import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class EmployeeIdentityStore implements IdentityStore {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	@Inject
	EmployeeUser user;
	
	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

		Employee employee = entityManager.find(Employee.class, Integer.parseInt(login.getPasswordAsString()));

		if (employee == null) {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
		
		String lastName = employee.getLastName();

		if (login.getCaller().equals(lastName)) {
			user.setId(Integer.parseInt(login.getPasswordAsString()));
			user.setName(login.getCaller());
			return new CredentialValidationResult(lastName, new HashSet<>(Arrays.asList("EMPLOYEE")));
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}

	}

}
