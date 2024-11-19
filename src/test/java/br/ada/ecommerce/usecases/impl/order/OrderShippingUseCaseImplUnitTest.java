package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class OrderShippingUseCaseImplUnitTest {

    // Mock == São objetos que fingem ser de um tipo determinado.
    // Implmentando comportamentos desejados para o teste.
    private IOrderRepository orderRepository;
    private IShippingNotifierUseCase notifierUseCase;
    private OrderShippingUseCaseImpl useCase;

    @BeforeEach
    public void setup() {
        // Mockito é uma biblioteca para criar objetos mock
        orderRepository = Mockito.mock(IOrderRepository.class);

        notifierUseCase = Mockito.mock(IShippingNotifierUseCase.class);

        useCase = new OrderShippingUseCaseImpl(
             orderRepository,
             notifierUseCase
        );
    }

    // Dado: Pedido com estado igual a pago
    // Quando: Realiza a entrega
    // Então: Deve atualizado estado para finalizado

    // Dado: Pedido com estado igual a aguardo pagamento
    // Quando: Realiza a entrega
    // Então: Não deve atualizar o estado

    // Dado: Pedido com estado igual a aguardando pagamento
    // Quando: Realiza a entrega
    // Então: Não deve atualizar a base de dados

    // Dado: Pedido com estado igual a aguardando pagamento
    // Quando: Realiza a entrega
    // Então: Não deve notificar sobre a entrega

    // Dado: Pedido com estado igual a finalizado
    // Quando: Realiza a entrega
    // Então: Devo obter falha

    // Dado: Pedido com estado igual a aberto
    // Quando: Realiza a entrega
    // Então: Devo obter IllegalStateForShippingException

}
