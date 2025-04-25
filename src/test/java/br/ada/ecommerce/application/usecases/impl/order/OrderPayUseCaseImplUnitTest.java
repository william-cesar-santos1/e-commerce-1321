package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderPayUseCaseImplUnitTest {

    private OrderPayUseCaseImpl useCase = new OrderPayUseCaseImpl();

//    Verificar status do pedido.
    // - Criar um pedido com status open
    // - Chamar ação de pagar
    // - Validar exceção
    @Test
    public void test_if_order_is_open_cannot_pay() {
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        var hasException = false;
        try {
            useCase.pay(order);
        } catch(RuntimeException ex) {
            hasException = true;
        }
        Assertions.assertEquals(true, hasException);
    }

//    Verificar mensagem da exceção.
    // - Criar um pedido com status open
    // - Chamar ação de pagar
    // - Validar mensagem da exceção

    //    Verificar se o status mudou para pago.
    @Test
    public void test_if_order_have_paid_state_after_action() {
        // - Criar um pedido com status PENDING_PAYMENT(dado)
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // - Chamar ação de pagar(quando)
        useCase.pay(order);

        // - Verificar se o status é paid(então)
        Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
    }

//    Verificar se o pedido esta nulo.
    // - Chamar ação de pagar com pedido nulo

}
