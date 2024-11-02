package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.order.IOrderShippingUseCase;
import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import jakarta.transaction.Transactional;

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
    @Transactional
    public void shipping(Order order) {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Pedido ainda não pago.");
        }
        order.setStatus(OrderStatus.FINISH);
        orderRepository.save(order);
        notifierUseCase.notify(order);
    }

}
