package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import org.springframework.stereotype.Service;

@Service
public class ShippingNotifierUseCaseImpl implements IShippingNotifierUseCase {

    @Override
    public void notify(Order order) {
        System.out.println("Notifying.....");
    }

}
