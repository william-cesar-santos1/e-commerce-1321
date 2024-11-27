package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.INotifierUseCase;
import br.ada.ecommerce.util.DataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

public class OrderPlaceUseCaseImplUnitTest {

    private INotifierUseCase<Order> orderNotifier;
    private OrderPlaceUseCaseImpl useCase;

    @BeforeEach
    public void setup() {
        orderNotifier = Mockito.mock(INotifierUseCase.class);

        useCase = new OrderPlaceUseCaseImpl(
                orderNotifier
        );
    }

    // Dado: pedido possui itens
    // Quando: finalizo carrinho
    // Então: pedido passa para estado de aguardando pagamento
    @Test
    public void pedidoPossuiItem_finalizoCarrinho_pedidoPassaParaAguardandoPagamento() {
        var order = DataBuilder.buildOrder();
        order.setStatus(OrderStatus.OPEN);

        useCase.placeOrder(order);

        Assertions.assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
    }

    @Test
    public void pedidoComEstadoFinalizado_finalizoCarrinho_devoObterFalha() {
        var order = DataBuilder.buildOrder();
        order.setStatus(OrderStatus.FINISH);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.placeOrder(order)
        );
    }

    @Test
    public void pedidoComEstadoOpen_finalizoCarrinho_devoObterSucesso() {
        var order = DataBuilder.buildOrder();
        order.setStatus(OrderStatus.OPEN);

        useCase.placeOrder(order);
    }

    @Test
    public void pedidoSemItens_finalizoCarrinho_devoObterFalha() {
        var order = DataBuilder.buildOrder();
        order.setItems(null);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.placeOrder(order)
        );
    }

    @Test
    public void pedidoComValorZero_finalizoCarrinho_devoObterFalha() {
        var item = DataBuilder.buildOrderItem(DataBuilder.buildProduct());
        item.setSaleValue(BigDecimal.ZERO);

        var order = DataBuilder.buildOrder();
        order.setItems(
                List.of(item)
        );

        Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.placeOrder(order)
        );
    }

    @Test
    public void pedidoComValorMaiorQueZero_finalizoCarrinho_devoObterFalha() {
        var item = DataBuilder.buildOrderItem(DataBuilder.buildProduct());
        item.setSaleValue(BigDecimal.TEN);
        item.setAmount(1);

        var order = DataBuilder.buildOrder();
        order.setItems(
                List.of(item)
        );

        useCase.placeOrder(order);
    }

    @Test
    public void pedidoComItens_fechoOPedido_deveNotificarOCliente() {
        var order = DataBuilder.buildOrder();

        useCase.placeOrder(order);

        Mockito.verify(orderNotifier, Mockito.times(1))
                .updated(order);
    }

}
