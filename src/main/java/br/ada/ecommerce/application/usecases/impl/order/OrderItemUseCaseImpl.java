package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderItem;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.model.Product;
import br.ada.ecommerce.application.usecases.order.IOrderItemUseCase;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OrderItemUseCaseImpl implements IOrderItemUseCase {

    private IOrderRepository repository;

    public OrderItemUseCaseImpl(final IOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem addItem(Order order, Product product, BigDecimal price, Integer amount) {
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new RuntimeException("Pedido em estado invalido");
        }
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setAmount(amount);
        item.setSaleValue(price);
        order.getItems().add(item);
        repository.save(order);
        return item;
    }

    @Override
    public OrderItem changeAmount(Order order, Product product, Integer amount) {
        OrderItem item = order.getItems().stream()
                .filter(it -> it.getProduct().getId() == product.getId())//Lambda
                .findAny().orElseThrow(() -> new RuntimeException("Item não faz parte do pedido"));
        item.setAmount(amount);
        return item;
    }

    @Override
    public void removeItem(Order order, Product product) {
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        Boolean removed = order.getItems()
                .removeIf(it -> it.getProduct().getId() == product.getId());

        if (!removed) {
            throw new RuntimeException("Produto não encontrado no pedido");
        }
    }

}
