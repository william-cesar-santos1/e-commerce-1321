package br.ada.ecommerce.usecases.order;

import br.ada.ecommerce.model.Order;

public interface ICreateInvoiceOrderUseCase {

    void create(Order order);

}
