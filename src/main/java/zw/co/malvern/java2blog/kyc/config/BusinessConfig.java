package zw.co.malvern.java2blog.kyc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zw.co.malvern.java2blog.kyc.api.CustomerController;
import zw.co.malvern.java2blog.kyc.business.CustomerService;
import zw.co.malvern.java2blog.kyc.business.CustomerServiceImpl;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepository;

@Configuration
@EnableJpaRepositories(basePackages = {"zw.co.malvern.java2blog.kyc.repository"})
@EntityScan(basePackages = {"zw.co.malvern.java2blog.kyc.domain"})
public class BusinessConfig {

    @Bean
    public CustomerService customerService(final CustomerRepository customerRepository){
        return new CustomerServiceImpl(customerRepository);
    }
}
