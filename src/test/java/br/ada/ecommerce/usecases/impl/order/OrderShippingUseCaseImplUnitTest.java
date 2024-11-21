package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.exception.IllegalStateForShippingException;
import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @Test
    public void pedidoComEstaPago_realizaAEntrega_deveAtualizarEstadoParaFinalizado() {
        var order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.ship(order);

        Assertions.assertEquals(OrderStatus.FINISH, order.getStatus());
        Mockito.verify(orderRepository, Mockito.times(1))
                .save(order);
    }

    // Dado: Pedido com estado igual a aguardo pagamento
    // Quando: Realiza a entrega
    // Então: Não deve atualizar o estado
    @Test
    public void pedidoComEstadoAguardandoPagamento_realizaAEntrega_naoDeveAtualizarOEstado() {
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        Assertions.assertThrows(
                IllegalStateForShippingException.class,
                () -> useCase.ship(order)
        );

        Assertions.assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
    }

    // Dado: Pedido com estado igual a aguardando pagamento
    // Quando: Realiza a entrega
    // Então: Não deve atualizar a base de dados
    @Test
    public void pedidoComEstadoAguardandoPagamento_realizaAEntrega_naoDeveAtualizarABaseDados() {
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        Assertions.assertThrows(
                IllegalStateForShippingException.class,
                () -> useCase.ship(order)
        );

        Mockito.verify(orderRepository, Mockito.never()).save(order);
    }

    // Dado: Pedido com estado igual a aguardando pagamento
    // Quando: Realiza a entrega
    // Então: Não deve notificar sobre a entrega
    @Test
    public void pedidoComEstadoAguardandoPagamento_realizaAEntrega_naoDeveNotificar() {
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        Assertions.assertThrows(
                IllegalStateForShippingException.class,
                () -> useCase.ship(order)
        );

        Mockito.verify(notifierUseCase, Mockito.never()).notify(order);
    }

    // Dado: Pedido com estado igual a aberto
    // Quando: Realiza a entrega
    // Então: Devo obter IllegalStateForShippingException
    @Test
    public void pedidoComEstadoIgualAFinalizado_realizaAEntrega_devoObterFalha() {
        var order = new Order();
        order.setStatus(OrderStatus.FINISH);

        Assertions.assertThrows(
                IllegalStateForShippingException.class,
                () -> useCase.ship(order)
        );
    }

}
