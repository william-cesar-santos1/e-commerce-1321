package br.ada.ecommerce.integration.custom;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.usecases.order.ICreateInvoiceOrderUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class CreateInvoiceOrderImpl implements ICreateInvoiceOrderUseCase {

    private WebClient client;

    public CreateInvoiceOrderImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public void create(Order order) {
        var invoice = new Invoice();
        invoice.setDocument(order.getCustomer().getDocument());
        invoice.setSalesAt(order.getOrderedAt().toLocalDate());
        order.getItems().stream()
                .map(item -> item.getSaleValue().multiply(BigDecimal.valueOf(item.getAmount())))
                .reduce((first, second) -> first.add(second))
                .ifPresent(value -> invoice.setAmount(value));
        client.post()
                .uri("/nota-fiscal")
                .bodyValue(invoice)
                .retrieve();
    }

}
