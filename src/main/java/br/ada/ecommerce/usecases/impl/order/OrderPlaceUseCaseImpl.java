package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.INotifierUseCase;
import br.ada.ecommerce.usecases.order.IOrderPlaceUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderPlaceUseCaseImpl implements IOrderPlaceUseCase {

//    private INotifierUseCase<Order> orderNotifier;

    public OrderPlaceUseCaseImpl() {
//        this.orderNotifier = orderNotifier;
    }

    @Override
    public void placeOrder(Order order) {
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido com estado invalido");
        }
        var total = amount(order);
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Pedido zerado, não é possível prosseguir");
        }
        order.setStatus(OrderStatus.PENDING_PAYMENT);
//        orderNotifier.updated(order);
    }

    private BigDecimal amount(Order order) {
        return order.getItems()
                .stream()
                .map(item -> item.getSaleValue().multiply(BigDecimal.valueOf(item.getAmount())))
                .reduce((first, second) -> first.add(second))
                .orElse(BigDecimal.ZERO);
    }

}
