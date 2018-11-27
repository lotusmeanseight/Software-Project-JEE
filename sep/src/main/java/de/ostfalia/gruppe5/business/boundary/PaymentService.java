package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Payment;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService extends AbstractTableJPAService<Payment> {

    public List<Payment> findByCheckNumber(String checknumber) {
        TypedQuery<Payment> query = this.getEntityManager().createQuery("Select p from Payment p where p.checkNumber=?1", Payment.class);
        query.setParameter(1, checknumber);
        return query.getResultList();
    }

    public PaymentService() {
        setEntityClass(Payment.class);
    }

}
