package br.ada.ecommerce.application.usecases.order;

import br.ada.ecommerce.application.model.Order;

public interface ICreateInvoiceOrderUseCase {

    void create(Order order);

}
