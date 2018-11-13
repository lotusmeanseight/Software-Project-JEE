package de.ostfalia.gruppe5.business.boundary;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@DeclareRoles({"EMPLOYEE, CUSTOMER"})
public abstract class AbstractLazyJPAService<T> extends AbstractJPAService<T> {

    @RolesAllowed("EMPLOYEE")
    public List<T> getAllTLazy(int first, int max){
        TypedQuery<T> query = getEntityManager().createNamedQuery(gettClass().getSimpleName() + ".findAll", gettClass());
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    } 

    @RolesAllowed("EMPLOYEE")
    public int countT(){
        Query query = getEntityManager().createNamedQuery(gettClass().getSimpleName() + ".countAll" );
        List<Long> resultList = query.getResultList();
        return resultList.get(0).intValue();
    }

}
