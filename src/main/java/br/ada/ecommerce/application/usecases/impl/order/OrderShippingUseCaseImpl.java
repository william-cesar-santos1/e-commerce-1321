package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.exception.IllegalStateForShippingException;
import br.ada.ecommerce.application.usecases.order.IOrderShippingUseCase;
import br.ada.ecommerce.application.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderShippingUseCaseImpl implements IOrderShippingUseCase {

    private IOrderRepository orderRepository;
    private IShippingNotifierUseCase notifierUseCase;

    public OrderShippingUseCaseImpl(
            IOrderRepository orderRepository,
            IShippingNotifierUseCase notifierUseCase
    ) {
        this.orderRepository = orderRepository;
        this.notifierUseCase = notifierUseCase;
    }

    @Override
    public void ship(Order order) {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new IllegalStateForShippingException("Pedido em estado invalido para entrega.");
        }
        order.setStatus(OrderStatus.FINISH);
        orderRepository.save(order);
        notifierUseCase.notify(order);
    }

}
