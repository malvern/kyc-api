package zw.co.malvern.java2blog.kyc.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.malvern.java2blog.kyc.business.CustomerService;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

@RestController
@RequestMapping("api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("create")
    public CommonResponse createAccount(@RequestBody Customer customer){
       return customerService.createCustomer(customer);
    }
}
