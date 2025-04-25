package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.exception.IllegalOrderStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderPayUseCaseImplUnitTest {

    private OrderPayUseCaseImpl orderPayUseCase = new OrderPayUseCaseImpl();

    @Test
    public void my_first_test() {
        System.out.println("Meu primeiro teste");
    }

    // Realizar o pagamento de um pedido que esteja com estado OPEN
    @Test
    public void test_if_order_have_open_state_throws_exception_when_i_try_pay() {
        // - Criar um pedido com status open
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        // - Validar exceção
        Assertions.assertThrows(IllegalOrderStateException.class, () ->
                // - Chamar ação de pagar
                orderPayUseCase.pay(order)
        );
    }

    // Realizar o pagamento de um pedido que já foi pago (estado PAID)
    @Test
    public void test_if_order_have_paid_state_throws_exception_when_i_try_pay() {
        // - Criar um pedido com status paid
        var order = new Order();
        order.setStatus(OrderStatus.PAID);

        // - Validar exceção
        Assertions.assertThrows(IllegalOrderStateException.class, () ->
                // - Chamar ação de pagar
                orderPayUseCase.pay(order)
        );
    }

    // Verificar se o status mudou para pago.
    @Test
    public void test_if_order_have_paid_state_after_action() {
        // - Criar um pedido com status PENDING_PAYMENT(dado)
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // - Chamar ação de pagar(quando)
        orderPayUseCase.pay(order);

        // - Verificar se o status é paid(então)
        Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
    }

}
