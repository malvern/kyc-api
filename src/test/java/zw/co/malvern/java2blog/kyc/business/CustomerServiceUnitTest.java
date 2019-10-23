package zw.co.malvern.java2blog.kyc.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepository;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    private Customer customer;


    @Before
    public void setup(){
        customerService = new CustomerServiceImpl(customerRepository);
        customer = new Customer();
        customer.setName("mal");
        customer.setSurname("3rn");
        customer.setAge(19L);
    }


    @Test
    public void givenCustomerWhenCreatingCustomerThenReturnCustomer(){
        when(customerRepository.save(customer)).thenReturn(customer);
        final CommonResponse response  = customerService.createCustomer(customer);
        assertNotNull(response);
        assertEquals("narrative","account created",response.getNarrative());
        assertEquals("success",true,response.isSuccess());
    }
}
