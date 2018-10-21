import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerIT {

//    EntityManager entityManager;
//
//    @Before
//    public void setup() {
//        entityManager = Persistence.createEntityManagerFactory("integration")
//                .createEntityManager();
//    }
//
//    @After
//    public void tearDown() {
//        entityManager.clear();
//        entityManager.close();
//    }
//
//    @Test
//    public void validJPAMapping() {
//        entityManager.getTransaction().begin();
//        Customer customer = new Customer();
//        customer.setFirstname("Haskell");
//        customer.setLastname("Rust");
//
//        entityManager.persist(customer);
//        assertTrue(entityManager.contains(customer));
//
//        Customer entityCustomer = entityManager.find(Customer.class, customer.getId());
//        assertEquals(entityCustomer.getFirstname(), customer.getFirstname());
//
//        entityManager.remove(customer);
//        assertTrue(!entityManager.contains(customer));
//
//        entityManager.getTransaction().commit();
//    }

}
