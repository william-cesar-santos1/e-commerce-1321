package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderPayUseCaseImplUnitTest {

    // Todo teste é constituído de entrada, processamento e saída conhecida.

    private Order order;
    private OrderPayUseCaseImpl useCase;

    @BeforeEach
    public void setup() {
        // Será executado antes de cada teste.
        order = new Order();

        useCase = new OrderPayUseCaseImpl();
    }

    // Dado: Pedido com estado de finalizado
    // Quando: Realizo o pagamento
    // Então: Obtenho uma falha
    @Test
    public void pedidoFinalizado_realizoPagamento_deveFalhar() {
        // dado
        order.setStatus(OrderStatus.FINISH);

        // quando
        // então
        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.pay(order)
        );
    }

    // Dado: Pedido com estado de aguardando pagamento
    // Quando: Realizo o pagamento
    // Então: Obtenho sucesso
    @Test
    public void pedidoAguardandoPagamento_realizoPagamento_deveObterSucesso() {
        // dado
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // quando
        useCase.pay(order);

        // entao
    }

    // Dado: Um pedido com estado aberto
    // Quando: Realizo o pagamento
    // Então: Obtenho falha
    @Test
    public void pedidoAberto_realizoPagamento_deveFalhar() {
        // dado
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        // quando
        // então
        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.pay(order)
        );
    }

    // Dado: Pedido com estado de aguardando pagamento
    // Quando: Realizo o pagamento
    // Então: O pedido fique com estado igual a pago
    @Test
    public void pedidoAguardandoPagamento_realizoPagamento_deveAlterarEstadoParaPago() {
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        useCase.pay(order);

        Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
    }

    // Dado: Pedido com estado aberto
    // Quando: Realizo o pagamento
    // Então: O estado do pedido deve ser preservado
    @Test
    public void pedidoComEstadoAberto_realizoPagamento_estadoDevePermanecerOMesmo() {
        // Dado
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        // Quando
        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.pay(order)
        );

        // Então
        Assertions.assertEquals(OrderStatus.OPEN, order.getStatus());
    }

}
