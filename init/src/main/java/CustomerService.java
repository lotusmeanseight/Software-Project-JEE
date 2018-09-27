import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class CustomerService {

    @PersistenceContext
    EntityManager em;

    public void save(Customer customer) {
        em.persist(customer);
    }

//    public List<Customer> getAllCustomers(){
//       return em.createQuery("SELECT c from Customer c", Customer.class);
//    }
}
