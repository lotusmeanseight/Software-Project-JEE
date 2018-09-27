import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class CustomerView {

    private Customer customer;

    @Inject
    private CustomerService service;

    public List<Customer> customers;

    public CustomerView() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void save() {
        service.save(customer);
    }
}
