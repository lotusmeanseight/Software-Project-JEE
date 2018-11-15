package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@DeclareRoles({"EMPLOYEE, CUSTOMER"})
@RolesAllowed("EMPLOYEE")
public abstract class AbstractJPAService<T> {

    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public T find(final String id){
        return getEntityManager().find(entityClass, id);
    }

    public T find(final Integer id){
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll(){
          return getEntityManager().createQuery("select t from" + entityClass.getName() + "t", entityClass).getResultList();
    }

    public void save(final T entity){
        getEntityManager().persist(entity);
    }

    public void delete(final T entity){
        getEntityManager().remove(entity);
    }

    public void deleteById(final String id){
        getEntityManager().remove(find(id));
    }

    public void deleteById(final Integer id){
        getEntityManager().remove(find(id));
    }

    public T update(final T entity){
        return getEntityManager().merge(entity);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @RolesAllowed({"EMPLOYEE,CUSTOMER"})
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
