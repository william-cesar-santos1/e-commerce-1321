package br.ada.ecommerce.port.custom;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.usecases.order.ICreateInvoiceOrderUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateInvoiceOrderImpl implements ICreateInvoiceOrderUseCase {

    @Override
    public void create(Order order) {
        var invoice = new Invoice();
        invoice.setDocument(order.getCustomer().getDocument());
        invoice.setSalesAt(order.getOrderedAt().toLocalDate());
        order.getItems().stream()
                .map(item -> item.getSaleValue().multiply(BigDecimal.valueOf(item.getAmount())))
                .reduce((first, second) -> first.add(second))
                .ifPresent(value -> invoice.setAmount(value));

        System.out.println("Nota fiscal gerado com sucesso");
    }

}
