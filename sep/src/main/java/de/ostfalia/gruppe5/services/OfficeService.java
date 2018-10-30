package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Office;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RolesAllowed("internal-user")
@Stateless
public class OfficeService {


    @PersistenceContext(name = "simple")
    EntityManager entityManager;

    public OfficeService() {

    }

    public void save(Office office) {
        entityManager.persist(office);
    }

    private Office findById(String id) {
        return entityManager.find(Office.class, id);
    }

    public void deleteById(String id) {
            entityManager.remove(findById(id));
    }

    public List<Office> getAllOffices() {
        return entityManager.createQuery("select o from Office o", Office.class).getResultList();
    }

    public Office update(Office office) {
        return entityManager.merge(office);
    }
}


