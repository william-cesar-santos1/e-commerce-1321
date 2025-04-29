package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateOrderUseCaseImplUnitTest {

    private CreateOrderUseCaseImpl createOrderUseCase;
    private IOrderRepository orderRepository;
    private ICustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        System.out.println("Executando o before each");
        orderRepository = Mockito.mock(IOrderRepository.class);
        customerRepository = Mockito.mock(ICustomerRepository.class);

        createOrderUseCase = new CreateOrderUseCaseImpl(orderRepository, customerRepository);
    }

    /* - Teste para cliente inexistente
        -- cliente não existe (consultar no banco e retorna nulo)
        -- crio um pedido
        -- Exceção
        Mock
     */
    @Test
    public void customerNotExists_whenCreateOrder_thenThrowsIllegalStateException() {
        Mockito.when(customerRepository.findByDocument("dummy-value")).thenReturn(null);

        var customer = new Customer();
        customer.setDocument("dummy-value");

        Assertions.assertThrows(IllegalStateException.class, () ->
                createOrderUseCase.create(customer)
        );
    }


    /* - Teste para cliente existente
        -- Cliente existe (consultar no banco e retornar valor)
        -- crio um pedido
        -- sucesso
     */
    @Test
    public void customerExists_whenCreateOrder_success() {
        Mockito.when(customerRepository.findByDocument("dummy-value")).thenReturn(new Customer());

        var customer = new Customer();
        customer.setDocument("dummy-value");

        createOrderUseCase.create(customer);
    }

    //Teste para pedido com estado em aberto
    //Teste pedido novo sem items
}
