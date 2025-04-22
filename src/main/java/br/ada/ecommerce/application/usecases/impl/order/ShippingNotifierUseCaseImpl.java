package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.usecases.order.IShippingNotifierUseCase;
import org.springframework.stereotype.Service;

@Service
public class ShippingNotifierUseCaseImpl implements IShippingNotifierUseCase {

    @Override
    public void notify(Order order) {
        System.out.println("Notifying.....");
    }

}
