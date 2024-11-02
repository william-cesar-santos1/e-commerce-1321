package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class OrderShippingUseCaseImplUnitTest {

    @InjectMocks
    private OrderShippingUseCaseImpl useCase;

    @Mock
    private IOrderRepository repository;
    @Mock
    private IShippingNotifierUseCase notifier;

    @Test
    public void pedido_so_deve_ser_entregue_se_estiver_pago() {
        // Criar dados compatíveis com o teste
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        //Chamar a execução dos testes
        useCase.shipping(order);
    }

    @Test
    public void pedido_com_status_diferente_de_pago_deve_retornar_excecao() {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);

        Assertions.assertThrows(RuntimeException.class,
                ()/*new Executable() {*/ -> /*public void execute() {*/
                        useCase.shipping(order)
                /*}}*/
        );//Lambda
    }

    @Test
    public void notificar_cliente_sobre_a_entregue() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.shipping(order);

        //Verique(onde, quantidade de vezes).acao
        Mockito.verify(notifier, Mockito.times(1))
                .notify(order);
    }

    @Test
    public void pedido_entregue_deve_ficar_com_status_de_finalizado() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.shipping(order);

        Assertions.assertEquals(OrderStatus.FINISH, order.getStatus());
        Mockito.verify(repository, Mockito.times(1))
                .save(order);
    }

}
