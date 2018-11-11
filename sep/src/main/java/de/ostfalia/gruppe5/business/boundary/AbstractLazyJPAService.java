package de.ostfalia.gruppe5.business.boundary;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractLazyJPAService<T> extends AbstractJPAService<T> {

    public List<T> getAllTLazy(int first, int max){
        TypedQuery<T> query = getEntityManager().createNamedQuery(gettClass().getSimpleName() + ".findAll", gettClass());
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public int countT(){
        Query query = getEntityManager().createNamedQuery("countAll" + gettClass().getSimpleName());
        List<Long> resultList = query.getResultList();
        return resultList.get(0).intValue();
    }

}
