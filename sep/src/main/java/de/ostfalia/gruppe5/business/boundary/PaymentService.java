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

    private final List<String> letters = new ArrayList<>(
            Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N", "O", "P",
                    "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                    "Z"));

    @Override
    public void save(Payment entity) {
        entity.setCheckNumber(generateRandomCheckNumber((ThreadLocalRandom.current().nextInt(0, 5)),
                (ThreadLocalRandom.current().nextInt(0, 10))));
        super.save((entity));
    }

    private String generateRandomCheckNumber(int numberOfLetters, int numbers) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < numberOfLetters; i++) {
            builder.append(letters.get(ThreadLocalRandom
                    .current().nextInt(0, 26)));
        }

        for (int i = 0; i < numbers; i++) {
            builder.append(ThreadLocalRandom.current()
                    .nextInt(1, 10));
        }

        return builder.toString();
    }

    public List<Payment> findByCheckNumber(String checknumber) {
        TypedQuery<Payment> query = this.getEntityManager().createQuery("Select p from Payment p where p.checkNumber=:checkNumber", Payment.class);
        query.setParameter("checkNumber", checknumber);
        return query.getResultList();
    }

    public PaymentService() {
        setEntityClass(Payment.class);
    }

}
