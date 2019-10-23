package zw.co.malvern.java2blog.kyc.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTestEntityManagerUnitTest {
    private Customer customer;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void setup(){
        customer = new Customer();
        customer.setName("mal");
        customer.setSurname("3rn");
        customer.setAge(19L);
    }

    @Test
    public void givenCustomerWhenCreatingCustomerThenReturnCustomer(){
        assertNotNull(customer);
        testEntityManager.persistAndFlush(customer);
        final Optional<Customer> returnedCustomer = customerRepository.findById(1L);
        assertNotNull(returnedCustomer.get().getId());
        assertEquals("name",customer.getName(),returnedCustomer.get().getName());
        assertEquals("surname",customer.getSurname(),returnedCustomer.get().getSurname());
        assertEquals(customer.getAge(),returnedCustomer.get().getAge(),0);
    }

}
