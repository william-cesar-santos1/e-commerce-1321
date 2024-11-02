package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreateOrderUseCaseImplUnitTest {

    private Customer customer;

    //1
    @InjectMocks
    private CreateOrderUseCaseImpl useCase;

    //2 objetos
    @Mock
    private IOrderRepository orderRepository;
    @Mock
    private ICustomerRepository customerRepository;

    //3
    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setDocument("unit-test");

        Mockito.when(customerRepository.findByDocument(Mockito.any()))/*Método que será chamado pela execução do programa*/
                .thenReturn(customer);/*Retorno que deve ser feito para essa chamada*/
    }

    @Test
    public void cliente_invalido_deve_retornar_excecao() {
        Mockito.when(customerRepository.findByDocument(Mockito.any()))/*Método que será chamado pela execução do programa*/
                .thenReturn(null);/*Retorno que deve ser feito para essa chamada*/

        Customer customer = new Customer();

        Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.create(customer));//Lambda
    }

    @Test
    public void cadastrar_pedido_para_cliente_valido() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
    }

    @Test
    public void pedido_cadastrado_deve_ter_estado_igual_a_aberto() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertEquals(OrderStatus.OPEN, created.getStatus());
    }

    @Test
    public void pedido_deve_ter_endereco() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getShippingAddress());
    }

    @Test
    public void pedido_criado_deve_estar_sem_items() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.getItems().isEmpty());
    }

}
