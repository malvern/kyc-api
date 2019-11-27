package zw.co.malvern.java2blog.kyc.api;

import org.springframework.web.bind.annotation.*;
import zw.co.malvern.java2blog.kyc.business.CustomerService;
import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

@CrossOrigin
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
