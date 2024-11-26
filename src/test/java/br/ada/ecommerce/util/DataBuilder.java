package br.ada.ecommerce.util;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderItem;
import br.ada.ecommerce.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DataBuilder {

    public static Customer buildCustomer() {
        var customer = new Customer();
        customer.setDocument("123456");
        return customer;
    }

    public static OrderItem buildOrderItem(Product product) {
        var item = new OrderItem();
        item.setAmount(5);
        item.setSaleValue(BigDecimal.TEN);
        item.setProduct(product);
        return item;
    }

    public static Product buildProduct() {
        return new Product();
    }

    public static Order buildOrder() {
        var order = new Order();
        order.setCustomer(buildCustomer());
        order.setOrderedAt(LocalDateTime.now());
        order.setItems(List.of(buildOrderItem(buildProduct())));
        return order;
    }

}
