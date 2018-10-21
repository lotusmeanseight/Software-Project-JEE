import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ostfalia.gruppe5.models.implementations.CustomerImpl;
import de.ostfalia.gruppe5.models.implementations.EmployeeImpl;

public class CustomerIT {

    EntityManager entityManager;

    @Before
    public void setup() {
        entityManager = Persistence.createEntityManagerFactory("integration")
                .createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.clear();
        entityManager.close();
    }

    @Test
    public void validJPAMapping() {
//        entityManager.getTransaction().begin();
//        
//        
//        
//        entityManager.persist(customer);
//        assertTrue(entityManager.contains(customer));
//
//        CustomerImpl entityCustomer = entityManager.find(CustomerImpl.class, customer.getCustomerNumber());
//        assertEquals(entityCustomer.getCustomerName(), customer.getCustomerName());
//
//        entityManager.remove(customer);
//        assertTrue(!entityManager.contains(customer));
//
//        entityManager.getTransaction().commit();
    }

}
