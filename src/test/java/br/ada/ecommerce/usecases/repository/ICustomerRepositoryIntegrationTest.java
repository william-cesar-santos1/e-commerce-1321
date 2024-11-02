package br.ada.ecommerce.usecases.repository;

import br.ada.ecommerce.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICustomerRepositoryIntegrationTest {

    @Autowired
    private ICustomerRepository repository;

    @Test
    @Order(1)
    public void inserir_cliente_com_sucesso() {
        Customer customer = new Customer();
        customer.setName("integration");
        customer.setDocument("test");
        repository.save(customer);
    }

    @Test
    @Order(2)
    public void consultar_cliente_cadastrado() {
        List<Customer> found = repository.findByName("integration");
        Assertions.assertFalse(found.isEmpty());
    }

}
