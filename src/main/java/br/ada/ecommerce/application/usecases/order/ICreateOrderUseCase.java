package br.ada.ecommerce.application.usecases.order;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.model.Order;

public interface ICreateOrderUseCase {

    Order create(Customer customer);

}
