package zw.co.malvern.java2blog.kyc.api;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import zw.co.malvern.java2blog.kyc.domain.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureJsonTesters
@RunWith(SpringRunner.class)
public class CustomerJsonUnitTest {

    @Autowired
    private JacksonTester<Customer> customerJacksonTester;
    private Customer customer;

    @Before
    public void setup(){
        customer = new Customer();
        customer.setName("mal");
        customer.setSurname("3rn");
        customer.setAge(19L);
    }


    @Test
    public void givenCustomerWhenSerializeThenReturnCustomerJson() throws Exception {
        assertThat(customerJacksonTester.write(customer)).hasJsonPathStringValue("@.name");
        assertThat(customerJacksonTester.write(customer)).hasJsonPathStringValue("@.surname");
        assertThat(customerJacksonTester.write(customer)).hasJsonPathNumberValue("@.age");
        assertThat(customerJacksonTester.write(customer)).
                extractingJsonPathStringValue("@.name").isEqualTo("mal");
        assertThat(customerJacksonTester.write(customer)).
                extractingJsonPathStringValue("@.surname").isEqualTo("3rn");
    }

    @Test
    public void givenCustomerWhenDeserializeThenReturnCustomerObject() throws Exception {
        final String content = "{\"name\":\"mal\",\"surname\":\"3rn\",\"age\":\"19\"}";
        assertThat(customerJacksonTester.parse(content).getObject().toString()).isEqualTo(customer.toString());
        assertThat(customerJacksonTester.parseObject(content).getName()).isEqualTo(customer.getName());
        assertThat(customerJacksonTester.parseObject(content).getSurname()).isEqualTo(customer.getSurname());
        assertThat(customerJacksonTester.parseObject(content).getAge()).isEqualTo(customer.getAge());
    }
}
