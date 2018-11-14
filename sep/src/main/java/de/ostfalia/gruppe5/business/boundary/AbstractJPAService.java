package de.ostfalia.gruppe5.business.boundary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

public abstract class AbstractJPAService<T> {

    private Class<T> tClass;

    @PersistenceContext
    private EntityManager entityManager;

    public T find(final String id){
        return getEntityManager().find(tClass, id);
    }

    public T find(final Integer id){
        return getEntityManager().find(tClass, id);
    }

    public List<T> findAll(){
          return getEntityManager().createQuery("select t from" + tClass.getName() + "t", tClass).getResultList();
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

    public Class<T> gettClass() {
        return tClass;
    }

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

}
