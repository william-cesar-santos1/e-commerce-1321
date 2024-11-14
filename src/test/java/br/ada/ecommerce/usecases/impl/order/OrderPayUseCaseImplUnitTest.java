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
    public void pagarPedidoEmEstadoFinalizado() {
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

    // Dado: Pedido com estado de aguardando
    // Quando: Realizo o pagamento
    // Então: Obtenho sucesso
    @Test
    public void pagarPedidoEmEstadoAguardando() {
        // dado
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // quando
        new OrderPayUseCaseImpl().pay(order);

        // entao
    }

}
