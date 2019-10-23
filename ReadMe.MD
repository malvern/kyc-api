## Spring Boot Testing

In this article,we will discuss Test Driven Development using SpringBoot covering Unit Tests and Integration tests.

To simplify this article we will create a simple 4-tier SpringBoot application that allows system users to capture customer personal details.

This application will be developed from  `Domain` ----> `Repository` ----> `Business` ---> `api`

Packages `utils` and `config` were created for responses and application configurations respectively.

### Background
Many times as developers we get stuck with the following questions when it comes to Test Driven Development.
  - What to Test?
  - Where to start?
  - How to continue?
  - How to know when you are done?
 
The answer to all these questions is start with **happy path** (normal path of execution through a use case ) then 
conclude with corner cases thus ***CORRECT***.(Conformance, Ordering, Range, Reference, Existence, Cardinality, Time).

In this article we will focus mainly on **happy path** for simplicity.

#### 1. Domain Layer
We need the following fields in the domain/entity class `name`,`surname` and `age`.

Using Test Driven Development(TDD)cycle let `CustomerUnitTest` drives the creation of `Customer` bean.

`spring-boot-starter-test` is used as it comes in with a lot of userful libraries for testing such as `JUnit4/5,AssertJ,Hamcrest,
Mockito,JSONAssert,JsonPath`.

Lets make use of JUnit4 library for `domain` unit test


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
                           assertEquals("age",19L,customer.getAge());
                       }
                   }


That's it for `domain` test.Since it makes our test *go green*.

#### 2. Repository Layer
This is the persistence layer.
Lets utilise `JpaRepository interface` that is provided by Spring via `spring-boot-starter-data-jpa`
 as our main dependency in designing `CustomerRepository` However,We need the following for tests isolation `DataJpaTest` and `SpringRunner`
 - DataJpaTest
 
   is used to test JPA layer,by default this annotation will do following this for us:
      - scan all `classes` annotated with `@Entity`
      - configures 'Spring data JPA repositories`
      - configures embedded database if it exists
      
*DataJpaTest are transactional and roll back at the end of each tests.*
   
 - SpringRunner
 
    is an alias for the `SpringJUnit4ClassRunner` and it tells `JUnit` to run using Spring’s testing support.
    
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
    
`TestEntityManager` can also be used for repository tests as it provides similar functionalities as 
 the standard `JPA EntityManager`.
 
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




#### 3. Business Layer
In this layer we are simply developing business logic(*how the application behaves*).
For instance  When creating `customer` what should be returned to by the *system* to  *system user* ?

What will happen if the `customer` details already exists in the ***system database***?

All these issues should be addressed here in this tutorial we will focus mainly on the first one.

Successfull creation of `customer`  will return `narrative(String)` and `success(true)`.

Business layer depends on the repository layer hence we will inject `CustomerRepository` bean into the business layer.

*Test should run in *isolation* hence to achieve `Mockito` library to *mock* `repository` layer*

*Programming to an `interface` lets start with `CustomerServiceUnitTest`*
 
`CustomerServiceUnitTest` : Test customer creation in business layer

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




#### 4. Api Layer(Endpoint)
Since business is now up and running.Lets design the endpoint
###### WebMvcTest or @AutoConfigureWebTestClient
  - auto configures Spring MVC infrastructure.(auto configures MockMVC)
  - limited to a single controller and is used in combination with mockBean to provide mock implementation

*Customer creation end point is defined as*: `api/create`

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
There is another way of testing the controller layer using `@WebTestClient`. WebClient works as the same as `@WebMvcTest`
                             
Testing ***Serialization*** and ***Deserialization*** of `Customer` object

###### JsonTest or AutoConfigureJsonTesters
- auto configures Json Mappers libraries such as Jackson ObjectMapper,Json and Gson.

Testing customer Serialization and Deserialization

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
 
 At this point we are done with our customer-kyc application.
 
 Lets do an integration test to see how other applications consume customer-kyc endpoint.
 
 #### 5. Consume Endpoint : Integration testing
 
 Test should run in isolation therefore SpringBoot provides functionality to test application without running it on `server`

###### RestClientTest

 -by default it auto configures `Jackson,GSON,JsonB,RestTemplateBuilder`,and adds support for `MockRestServiceServer`
 
  `SpringBootTest` will not start a serve      
                          
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

             
We have simplified `integration test` so that it doesn't return created `customer object`,however in a scenario were a
`customer object` is required .e.g find by id there will be a need to create a database.
`SpringBoot` provides in-memory database for this e.g `H2 database`

Since we are done with all test cases on all layers lets create a test suite.

###### Test Suite
is a Composite of Tests and runs a collection of test cases.  

TestSuite can extract the tests to be run automatically.


### Conclusion
This article gives a quick introduction to `Rest endpoint api TDD` using `SpringBoot` from `Domain` to `Rest Api`.

### Dependencies
 - spring-boot-starter-data-jpa
 - JUnit 
 - h2 (not used in this tutorial)
 - spring-boot-starter-web
 - spring-boot-starter-test
                     

### assummed knowledge
- Spring boot configuration - in this article we avoided scattering of configurations by placing them in a single class
  `BusinessConfig`


