package br.ada.ecommerce.usecases.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;

public interface ICreateOrderUseCase {

    Order create(Customer customer);

}
