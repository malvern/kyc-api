package zw.co.malvern.java2blog.kyc.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class CustomerUnitTest {
    private Customer customer;

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
        assertEquals("name","mal",customer.getName());
        assertEquals("surname","3rn",customer.getSurname());
        assertEquals(19L,customer.getAge(),0);
    }
}
