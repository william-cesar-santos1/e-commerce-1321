package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderItem;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.exception.IllegalOrderStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class OrderPlaceUseCaseImplUnitTest {

    private OrderPlaceUseCaseImpl orderPlaceUseCase = new OrderPlaceUseCaseImpl();

    /*
    - pedido esta com estado de aguardando pagamento, fechar esse pedido deve resultar em exceção.
        -- Pedido com estado aguardando pagamento
        -- Chamar a ação de fechar
        -- Deve lançar a exceção
     */
    @Test
    public void orderIsPendingPayment_whenPlaceOrder_thenThrowsIllegalOrderStateException() {
        //pedidoAguardandoPagamento_quandoEuTentoFechar_entaoDeveLancarIllegalOrderStateException
        var order = new Order();
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        Assertions.assertThrows(IllegalOrderStateException.class, () ->
                orderPlaceUseCase.placeOrder(order)
        );
    }

    /*
    - pedido com estado de open e sem itens, não deve ser possível fechar.
        -- Pedido com estado open, porém sem itens.
        -- Chamar a ação de fechar
        -- Deve lançar a exceção
     */
    @Test
    public void orderIsOpenAndWithoutItems_whenPlaceOrder_thenThrowsIllegalStateOrderException() {
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);
        order.setItems(List.of());

        Assertions.assertThrows(IllegalOrderStateException.class, () ->
                orderPlaceUseCase.placeOrder(order)
        );
    }

    /*
    - pedido com estado de open e cem itens, deve ser alterado para aguardando pagamento.
        -- Pedido com esta de open, contendo itens
        -- Chamar a ação de fechar
        -- Deve alterar o estado para aguardando pagamento
     */
    @Test
    public void orderIsOpenAndHas100Items_whenPlaceOrder_thenOrderIsPendingPayment() {
        var order = new Order();
        order.setStatus(OrderStatus.OPEN);

        var items = IntStream.range(0, 100)
                .mapToObj(value -> new OrderItem())
                .peek(item -> {
                    item.setAmount(10);
                    item.setSaleValue(BigDecimal.TEN);
                }).toList();
        order.setItems(items);

        orderPlaceUseCase.placeOrder(order);

        Assertions.assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
    }

}
