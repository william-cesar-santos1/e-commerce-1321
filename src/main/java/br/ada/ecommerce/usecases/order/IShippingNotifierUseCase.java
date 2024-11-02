package br.ada.ecommerce.usecases.order;

import br.ada.ecommerce.model.Order;

public interface IShippingNotifierUseCase {

    void notify(Order order);

}
