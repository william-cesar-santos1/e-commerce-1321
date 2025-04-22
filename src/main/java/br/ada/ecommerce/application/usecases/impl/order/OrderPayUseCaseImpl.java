package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.order.IOrderPayUseCase;
import org.springframework.stereotype.Service;

@Service
public class OrderPayUseCaseImpl implements IOrderPayUseCase {

    @Override
    public void pay(Order order) {
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Pedido em estado invalido");
        }
        order.setStatus(OrderStatus.PAID);
    }

}
