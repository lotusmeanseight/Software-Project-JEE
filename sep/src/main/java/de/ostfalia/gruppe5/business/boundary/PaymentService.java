package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.Payment;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RolesAllowed("EMPLOYEE")
@Stateless
public class PaymentService extends AbstractTableJPAService<Payment> {

    public List<Payment> findByCheckNumber(String checknumber) {
        TypedQuery<Payment> query = this.getEntityManager().createQuery("Select p from Payment p where p.checkNumber=:checkNumber", Payment.class);
        query.setParameter("checkNumber", checknumber);
        return query.getResultList();
    }

    public PaymentService() {
        setEntityClass(Payment.class);
    }

}
