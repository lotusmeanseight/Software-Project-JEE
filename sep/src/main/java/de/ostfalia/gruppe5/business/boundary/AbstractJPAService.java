package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
public abstract class AbstractJPAService<T> {

	private Class<T> entityClass;

	@PersistenceContext(unitName = "simple")
	private EntityManager entityManager;

	@RolesAllowed("CUSTOMER")
	public T find(final String id) {
		return getEntityManager().find(entityClass, id);
	}

	@RolesAllowed("CUSTOMER")
	public T find(final Integer id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		CriteriaQuery<T> all = criteriaQuery.select(root);
		TypedQuery<T> allQuery = entityManager.createQuery(all);

		return allQuery.getResultList();
	}

	@RolesAllowed("CUSTOMER")
	public void save(final T entity) {
		getEntityManager().persist(entity);
	}

	public void delete(final T entity) {
		getEntityManager().remove(entity);
	}

	public void deleteById(final String id) {
		getEntityManager().remove(find(id));
	}

	public void deleteById(final Integer id) {
		getEntityManager().remove(find(id));
	}

	@RolesAllowed("CUSTOMER")
	public T update(final T entity) {
		return getEntityManager().merge(entity);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@RolesAllowed({ "EMPLOYEE,CUSTOMER" })
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
