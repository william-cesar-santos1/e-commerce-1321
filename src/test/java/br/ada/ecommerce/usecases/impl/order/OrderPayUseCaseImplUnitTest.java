package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderPayUseCaseImplUnitTest {

    // Todo teste é constituído de entrada, processamento e saída conhecida.

    // Dado: Pedido com estado de finalizado
    // Quando: Realizo o pagamento
    // Então: Obtenho uma falha
    @Test
    public void pedidoFinalizado_realizoPagamento_deveFalhar() {
        // dado
        var order = new Order();
        order.setStatus(OrderStatus.FINISH);

        // quando
        var exceptionWasThrow = false;
        try {
            new OrderPayUseCaseImpl().pay(order);
        } catch (RuntimeException ex) {
            exceptionWasThrow = true;
        }

        //Entao
        Assertions.assertTrue(exceptionWasThrow);
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
        new OrderPayUseCaseImpl().pay(order);

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
        var exceptionWasThrow = false;
        try {
            new OrderPayUseCaseImpl().pay(order);
        } catch (RuntimeException ex) {
            exceptionWasThrow = true;
        }

        //Entao
        Assertions.assertTrue(exceptionWasThrow);
    }

    // Dado: Pedido com estado de aguardando pagamento
    // Quando: Realizo o pagamento
    // Então: O pedido fique com estado igual a pago
    @Test
    public void pedidoAguardandoPagamento_realizoPagamento_deveAlterarEstadoParaPago() {
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        new OrderPayUseCaseImpl().pay(order);

        Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
    }

    // Dado: Pedido com estado aberto
    // Quando: Realizo o pagamento
    // Então: O estado do pedido deve ser preservado
    @Test
    public void pedidoComEstadoAberto_realizoPagamento_estadoDevePermanecerOMesmo() {
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        try {
            new OrderPayUseCaseImpl().pay(order);
        } catch (RuntimeException ex) {
        }

        Assertions.assertEquals(OrderStatus.OPEN, order.getStatus());
    }

}
