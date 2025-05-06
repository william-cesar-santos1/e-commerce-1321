package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class OrderShippingUseCaseImplUnitTest {

    private IOrderRepository orderRepository;
    private IShippingNotifierUseCase notifierUseCase;
    private OrderShippingUseCaseImpl useCase;

    @BeforeEach
    public void setup() {
        orderRepository = Mockito.mock();
        notifierUseCase = Mockito.mock();

        useCase = new OrderShippingUseCaseImpl(orderRepository, notifierUseCase);
    }

    /* - Teste para garantir que o usuário foi notificado sobre a entrega
         -- Dado que possuo um pedido com estado de pago
         -- Quando eu realizar a entrega
         -- Envie uma notificação para o cliente
      */
    @Test
    public void givenOrderIsPaid_whenIShipOrder_thenSendNotification() {
        var order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.ship(order);

        Mockito.verify(notifierUseCase, Mockito.times(1))
                .notify(order);
    }

    /* - Teste para garantir que o usuário foi notificado sobre a entrega
        -- Dado que possuo um pedido com estado de pago
        -- Quando eu realizar a entrega
        -- Then pedido esteja com estado de FINISH
     */
    @Test
    public void givenOrderIsPaid_whenIShipOrder_thenOrderIsFinishState() {
        var order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.ship(order);

        var captor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository, Mockito.times(1))
                .save(captor.capture());
        var orderSaved = captor.getValue();
        Assertions.assertEquals(OrderStatus.FINISH, orderSaved.getStatus());
    }


}
