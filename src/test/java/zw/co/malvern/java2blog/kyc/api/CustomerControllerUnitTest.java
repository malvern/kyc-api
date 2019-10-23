package zw.co.malvern.java2blog.kyc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zw.co.malvern.java2blog.kyc.business.CustomerService;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommonResponse response;
    private Customer customer;

    @Before
    public void setup(){
        response = new CommonResponse();
        customer = new Customer();
        customer.setName("mal");
        customer.setSurname("3rn");
        customer.setAge(19L);
    }

    @Test
    public void givenCustomerWhenCreatingCustomerThenReturnCustomer() throws Exception {
        response.setSuccess(true);
        response.setNarrative("account created");
        given(customerService.createCustomer(any(Customer.class))).willReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/create").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk()).
                andExpect(jsonPath("success").value(true)).
                andExpect(jsonPath("narrative").value("account created"));

    }

}
