package br.ada.ecommerce.usecases.repository;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IOrderRepositoryIntegrationTest {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IProductRepository productRepository;

    private Order order;

    @BeforeEach
    public void setup() {
        var customer = new Customer();
        customer.setName("William");
        customerRepository.save(customer);

        var product = new Product();
        product.setBarcode("123456");
        product.setDescription("dummy-value");
        product.setPrice(BigDecimal.TEN);
        productRepository.save(product);

        var item = new OrderItem();
        item.setAmount(10);
        item.setSaleValue(BigDecimal.TEN);
        item.setProduct(product);

        var items = new ArrayList<OrderItem>();
        items.add(item);

        order = new Order();
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.OPEN);
        order.setShippingAddress("Minha casa");
        order.setCustomer(customer);
        order.setItems(items);

        repository.save(order);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void order_alreadyPersist_shouldHaveId() {
        Assertions.assertNotNull(order.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void orderHasItem_searchOrder_foundItems() {
        var found = repository.findById(order.getId())
                .orElseThrow();
        Assertions.assertNotNull(found);
        Assertions.assertNotNull(found.getItems());
        Assertions.assertFalse(found.getItems().isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(1000)
    public void delete() {
        repository.delete(order);
    }

}
