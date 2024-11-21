package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateOrderUseCaseImplUnitTest {

    private IOrderRepository orderRepository;
    private ICustomerRepository customerRepository;

    private CreateOrderUseCaseImpl useCase;
    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setDocument("dummy-value");

        orderRepository = Mockito.mock(IOrderRepository.class);

        customerRepository = Mockito.mock(ICustomerRepository.class);
        Mockito.when(customerRepository.findByDocument("dummy-value"))
                .thenReturn(customer);

        useCase = new CreateOrderUseCaseImpl(
                orderRepository,
                customerRepository
        );
    }

    @Test
    public void clienteExistente_cadastroPedido_obtenhoSucesso() {
        var order = useCase.create(customer);
        Assertions.assertNotNull(order);
    }

    @Test
    public void clienteNaoExiste_cadastraoPedido_devoObterExcecao() {
        Mockito.when(customerRepository.findByDocument("null-value"))
                .thenReturn(null);

        var customer = new Customer();
        customer.setDocument("null-value");

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.create(customer)
        );
    }

}
