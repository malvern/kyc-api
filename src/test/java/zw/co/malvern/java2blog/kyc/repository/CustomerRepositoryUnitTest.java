package zw.co.malvern.java2blog.kyc.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryUnitTest {
    private Customer customer;
    @Autowired
    private CustomerRepository customerRepository;

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
        final Customer savedCustomer = customerRepository.save(customer);
        assertNotNull(savedCustomer.getId());
        assertEquals("name",customer.getName(),savedCustomer.getName());
        assertEquals("surname",customer.getSurname(),savedCustomer.getSurname());
        assertEquals(customer.getAge(),savedCustomer.getAge(),0);
    }


}
