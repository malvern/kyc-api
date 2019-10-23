package zw.co.malvern.java2blog.kyc.integration;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationUnitTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Customer customer;
    @Before
    public void setup(){
        customer = new Customer();
        customer.setName("mal");
        customer.setSurname("3rn");
        customer.setAge(19L);
    }

    @Test
    public void createCustomerShouldReturnSuccess() throws Exception{
        final String baseUrl = "/api/create";
        ResponseEntity<CommonResponse> response = testRestTemplate.
                exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(customer,httpHeaders()),CommonResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNarrative()).isEqualTo("account created");
        assertThat(response.getBody().isSuccess()).isEqualTo(true);
       }

    private HttpHeaders httpHeaders(){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
        return httpHeaders;
    }
}
