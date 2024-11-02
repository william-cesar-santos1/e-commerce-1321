package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderItem;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.model.Product;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class OrderItemUseCaseImplUnitTest {

    @Mock
    private IOrderRepository repository;

    @InjectMocks
    OrderItemUseCaseImpl useCase;

    @Test
    public void inserir_item_em_pedido_com_estado_igual_a_novo() {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);

        Product product = new Product();

        useCase.addItem(order, product, BigDecimal.TEN, 2);

        OrderItem found = order.getItems()
                .stream().filter(it -> it.getProduct() == product).findAny()
                .orElse(null);
        Assertions.assertNotNull(found);
    }

    @Test
    public void nao_deve_inserir_item_em_pedido_com_estado_igual_a_pago() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        Product product = new Product();

        Assertions.assertThrows(RuntimeException.class,
                () -> useCase.addItem(order, product, BigDecimal.TEN, 2)
        );
    }

    @Test
    public void deve_atualizar_o_pedido_apos_inserir_item() {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);

        Product product = new Product();

        useCase.addItem(order, product, BigDecimal.TEN, 2);

        Mockito.verify(repository, Mockito.times(1)).save(order);
    }

    @Test
    public void deve_alterar_a_quantidade_do_item_para_um_produto_existente() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);
        order.setItems(new ArrayList<>());

        Product product = new Product();

        OrderItem item = new OrderItem();
        item.setProduct(product);

        order.getItems().add(item);

        useCase.changeAmount(order, product, 5);
    }

    @Test
    public void nao_deve_alterar_a_quantidade_do_item_para_um_produto_inexistente() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        Product product = new Product();

        Assertions.assertThrows(RuntimeException.class,
                () -> useCase.changeAmount(order, product, 5)
        );
    }

    @Test
    public void deve_remover_item_existente_no_pedido() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);
        order.setItems(new ArrayList<>());

        Product product = new Product();

        OrderItem item = new OrderItem();
        item.setProduct(product);

        order.getItems().add(item);

        useCase.removeItem(order, product);
    }

    @Test
    public void nao_deve_remover_item_inexistente_no_pedido() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);
        order.setItems(new ArrayList<>());

        Product product = new Product();

        Assertions.assertThrows(RuntimeException.class,
                () -> useCase.removeItem(order, product)
        );
    }

}
