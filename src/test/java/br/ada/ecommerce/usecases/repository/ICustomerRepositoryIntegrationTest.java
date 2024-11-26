package br.ada.ecommerce.usecases.repository;

import br.ada.ecommerce.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ICustomerRepositoryIntegrationTest {

    @Autowired
    private ICustomerRepository repository;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setName("William");
        customer.setDocument("dummy-value");

        repository.save(customer);
    }

    @AfterEach
    public void destroy() {
        repository.delete(customer);
    }

    @Test
    public void cliente_jaCadastrado_deveTerId() {
        Assertions.assertNotNull(customer.getId());
    }

    @Test
    public void clienteJaCadastrado_pesquisoPorNome_deveRetornar() {
        var lista = repository.findByName("William");
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(customer.getId(), lista.get(0).getId());
    }

    @Test
    public void clienteNaoCadastrado_pesquisoPorNome_deveRetornarVazio() {
        var lista = repository.findByName("integration-test");
        Assertions.assertNotNull(lista);
        Assertions.assertTrue(lista.isEmpty());
    }

    @Test
    public void clienteJaCadastrado_pesquisoPorDocument_deveRetornar() {
        var cliente = repository.findByDocument("dummy-value");
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(customer.getId(), cliente.getId());
    }

}