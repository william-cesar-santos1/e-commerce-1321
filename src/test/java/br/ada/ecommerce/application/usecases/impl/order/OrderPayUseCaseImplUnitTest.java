package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.exception.IllegalOrderStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderPayUseCaseImplUnitTest {

    private OrderPayUseCaseImpl orderPayUseCase = new OrderPayUseCaseImpl();

    @Test
    public void my_first_test() {
        System.out.println("Meu primeiro teste");

        var first = BigDecimal.valueOf(10);
        var second = BigDecimal.valueOf(10.00000);

        Assertions.assertTrue(first.compareTo(second) == 0);
        // Assertions.assertEquals(); - Faz a comparação entre dois valores, garante que sejam iguais. Cuidado com os tipos, os dois objetos precisam ser do memso tipo.
        // Assertions.assertNull(); - Valida que a variável esteja com valor nulo.
        // Assertions.assertNotNull(); - Valida que a variável não esteja nula.
        // Assertions.assertTrue(); - Valida que o resultado da expressão seja verdadeiro.
        // Assertions.assertThrows() - Valida que tenha ocorrido uma exceção durante a chamada do método.
    }

    // Realizar o pagamento de um pedido que esteja com estado OPEN
    @Test
    public void orderIsOpen_whenPayOrder_throwsIllegalOrderStateException() {
        // Dado, quando e então é ourta forma de dizer arrange, action and assert(AAA)
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
    public void orderIsPaid_whenPayOrder_throwsIllegalOrderStateException() {
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
    public void orderIsPendingPayment_whenPayOrder_thenOrderIsPaid() {
        // - Criar um pedido com status PENDING_PAYMENT(dado)
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // - Chamar ação de pagar(quando)
        orderPayUseCase.pay(order);

        // - Verificar se o status é paid(então)
        Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
    }

}
