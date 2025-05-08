package br.ada.ecommerce.application.usecases.repository;

import br.ada.ecommerce.application.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wiremock.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ICustomerRepositoryIntegrationTest {

    @Autowired
    private ICustomerRepository repository;

//    @BeforeEach
//    public void setup() {
//        IntStream.range(0, 10)
//                .forEach(index -> {
//                    var customer = new Customer();
//                    customer.setDocument("unit-test-" + index);
//                    customer.setEmail(List.of("unit-test-" + index));
//                    customer.setTelephone(List.of("unit-test-" + index));
//                    customer.setName("unit-test-" + index);
//                    customer.setBirthDate(LocalDate.now());
//                    repository.save(customer);
//                    System.out.println("Customer com document " + customer.getDocument()
//                            + " e nome " + customer.getName() + " foi salvo na base de dados");
//                });
//    }

    @Test
    public void givenCustomerExists_whenIFindByDocument_thenFoundCustomer() {
        var document = RandomStringUtils.randomNumeric(15);
        var customer = new Customer();
        customer.setDocument(document);
        customer.setEmail(List.of("integration@test.com"));
        customer.setTelephone(List.of("5545000000000"));
        customer.setName("integration test");
        customer.setBirthDate(LocalDate.now());
        repository.save(customer);

        var found = repository.findByDocument(document);

        Assertions.assertNotNull(found);
    }

    @Test
    public void givenCustomerNotExists_whenIFindByDocument_thenReturnNull() {
        var customer = repository.findByDocument(RandomStringUtils.randomAlphanumeric(15));
        Assertions.assertNull(customer);
    }

    @Test
    public void givenCustomerExists_whenIFindByName_thenFoundCustomer() {
        var name = RandomStringUtils.randomAlphabetic(20);
        var customer = new Customer();
        customer.setDocument("000000000");
        customer.setEmail(List.of("integration@test.com"));
        customer.setTelephone(List.of("5545000000000"));
        customer.setName(name);
        customer.setBirthDate(LocalDate.now());
        repository.save(customer);

        var customers = repository.findByName(name);
        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.isEmpty());
    }

    @Test
    public void givenCustomerNotExists_whenIFindByName_thenReturnEmptyList() {
        var customers =
    }

//    @AfterEach
//    public void destroy() {
//        repository.deleteAll();
//        System.out.println("Apagado dos os clientes da base de dados");
//    }

}
