package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class OrderPayUseCaseImplUnitTest {

    /*
     * 1 - Pedido precisa estar com status == OrderStatus.PENDING_PAYMENT
     * 2 - Pedido deve passar a ter o status igual a OrderStatus.PAID
     * 3 - Notificar o cliente sobre o pagamento com sucesso
     */
    @InjectMocks
    private OrderPayUseCaseImpl useCase;

    @Test
    public void pagar_pedido_com_estado_igual_PENDING_PAYMENT() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        useCase.pay(order);
    }

    @Test
    public void nao_deve_pagar_pedido_com_estado_igual_OPEN() {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);

        Assertions.assertThrows(RuntimeException.class,
                () -> useCase.pay(order)
        );
    }

}
