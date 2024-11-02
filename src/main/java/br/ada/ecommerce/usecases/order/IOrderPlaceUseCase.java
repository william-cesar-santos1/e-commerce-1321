package br.ada.ecommerce.usecases.order;

import br.ada.ecommerce.model.Order;

public interface IOrderPlaceUseCase {

    /*
     * 1 - Pedido precisa estar com status == OrderStatus.OPEN
     * 2 - Pedido precisa ter no mínimo um item
     * 3 - Valor deve ser maior que zero
     * 4 - Notificar o cliente que esta aguardando o pagamento
     * 5 - Pedido deve passar a ter o status igual OrderStatus.PENDING_PAYMENT
     */
    void placeOrder(Order order);

}
