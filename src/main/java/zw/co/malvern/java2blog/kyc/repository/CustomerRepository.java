package zw.co.malvern.java2blog.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.malvern.java2blog.kyc.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
