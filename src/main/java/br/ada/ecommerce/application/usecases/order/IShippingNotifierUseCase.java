package br.ada.ecommerce.application.usecases.order;

import br.ada.ecommerce.application.model.Order;

public interface IShippingNotifierUseCase {

    void notify(Order order);

}
