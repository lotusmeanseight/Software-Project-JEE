package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Product;
import de.ostfalia.gruppe5.models.ProductLine;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class ProductLineService {


    EntityManager entityManager;

    public ProductLineService() {

    }

    public void save(ProductLine productLine) {
        entityManager.persist(productLine);
    }

    private ProductLine findById(String id) {
        return entityManager.find(ProductLine.class, id);
    }

    public void deleteById(String id) {
        entityManager.remove(findById(id));
    }

    public List<ProductLine> getAllProductLines() {
        return entityManager.createQuery("select p from ProductLine p", ProductLine.class).getResultList();
    }

    public ProductLine update(ProductLine productLine) {
        return entityManager.merge(productLine);
    }
}
