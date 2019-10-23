package zw.co.malvern.java2blog.kyc.business;

import zw.co.malvern.java2blog.kyc.domain.Customer;
import zw.co.malvern.java2blog.kyc.utils.CommonResponse;

public interface CustomerService {
    CommonResponse createCustomer(Customer customer);
}
