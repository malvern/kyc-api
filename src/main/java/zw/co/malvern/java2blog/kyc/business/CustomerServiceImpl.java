package zw.co.malvern.java2blog.kyc.business;

import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepository;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CommonResponse createCustomer(Customer customer) {
        final CommonResponse response = new CommonResponse();
        final Customer savedCustomer = customerRepository.save(customer);
        response.setNarrative("account created");
        response.setSuccess(true);
        return response;
    }
}
