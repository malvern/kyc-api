package zw.co.malvern.java2blog.kyc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import zw.co.malvern.java2blog.kyc.api.CustomerControllerUnitTest;
import zw.co.malvern.java2blog.kyc.api.CustomerJsonUnitTest;
import zw.co.malvern.java2blog.kyc.business.CustomerServiceUnitTest;
import zw.co.malvern.java2blog.kyc.domain.CustomerUnitTest;
import zw.co.malvern.java2blog.kyc.integration.CustomerIntegrationUnitTest;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepositoryTestEntityManagerUnitTest;
import zw.co.malvern.java2blog.kyc.repository.CustomerRepositoryUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerControllerUnitTest.class,
        CustomerJsonUnitTest.class,
        CustomerServiceUnitTest.class,
        CustomerUnitTest.class,
        CustomerIntegrationUnitTest.class,
        CustomerRepositoryTestEntityManagerUnitTest.class,
        CustomerRepositoryUnitTest.class
})
public class KycApiTestSuite {


}
