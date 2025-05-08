package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.usecases.exception.UseCaseException;
import br.ada.ecommerce.application.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import br.ada.ecommerce.application.usecases.repository.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseImplUnitTest {

    // Com o uso do @Mock e @InjectMocks não é necessário fazer o setup,
    // pois ele é todo configurado via MockitoExtension

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    /* - Teste para cliente inexistente
        -- cliente não existe (consultar no banco e retorna nulo)
        -- crio um pedido
        -- Exceção
        Mock
     */
    @Test
    public void customerNotExists_whenCreateOrder_thenThrowsIllegalStateException() {
        // Exemplo de como criar um mock -> Mockito.mock(ICustomerRepository.class);
        System.out.println("customerNotExists_whenCreateOrder_thenThrowsIllegalStateException");
        Mockito.when(customerRepository.findByDocument("dummy-value")).thenReturn(null);

        var customer = new Customer();
        customer.setDocument("dummy-value");

        Assertions.assertThrows(IllegalStateException.class, () ->
                createOrderUseCase.create(customer)
        );
    }


    /* - Teste para cliente existente
        -- Cliente existe (consultar no banco e retornar valor)
        -- crio um pedido
        -- sucesso
     */
    @Test
    public void customerExists_whenCreateOrder_success() {
        System.out.println("customerExists_whenCreateOrder_success");
        Mockito.when(customerRepository.findByDocument("dummy-value")).thenReturn(new Customer());

        var customer = new Customer();
        customer.setDocument("dummy-value");

        createOrderUseCase.create(customer);
    }

    //Teste para pedido com estado em aberto
    //Teste pedido novo sem items

    /* - Teste para falha na comunicação com o repository
        -- Dado que o cliente existe
        -- Quando eu crio um pedido, o repository lança uma exceção
        -- Então a exceção deve ser relançada
     */
    @Test
    public void givenCustomerExists_whenCreateNewOrderAndRepositoryThrowsException_thenReThrowAnyUseCaseException() {
        System.out.println("givenCustomerExists_whenCreateNewOrderAndRepositoryThrowsException_thenReThrowAnyUseCaseException");
        Mockito.when(customerRepository.findByDocument("some-value")).thenReturn(new Customer());
        Mockito.when(orderRepository.save(Mockito.any())).thenThrow(RepositoryException.class);

        var customer = new Customer();
        customer.setDocument("some-value");

        Assertions.assertThrows(UseCaseException.class, () ->
                createOrderUseCase.create(customer)
        );
    }

    // Dado que o cliente existe.
    // Quando eu tentar consultar pelo documento, deve lançar uma RepositoryException
    // Então o use case deve relançar como UseCaseException
    @Test
    public void givenCustomerExists_whenCreateNewOrderAndCustomerRepositoryThrowsException_thenThrowUseCaseException() {
        System.out.println("givenCustomerExists_whenCreateNewOrderAndCustomerRepositoryThrowsException_thenThrowUseCaseException");
        Mockito.when(customerRepository.findByDocument("unit-test")).thenThrow(RepositoryException.class);

        var customer = new Customer();
        customer.setDocument("unit-test");

        Assertions.assertThrows(UseCaseException.class, () ->
                createOrderUseCase.create(customer)
        );
    }

    /* - Teste para garantir a consulta de cliente
        -- Dado que possuo o CPF do cliente
        -- Eu crio um pedido
        -- Deve consultar esse cliente pelo CPF
     */
    @Test
    public void givenCustomerWithDocument_whenICreateNewOrder_shouldSearchCustomer() {
        System.out.println("givenCustomerWithDocument_whenICreateNewOrder_shouldSearchCustomer");
        Mockito.when(customerRepository.findByDocument("aula-05-04")).thenReturn(new Customer());

        var customer = new Customer();
        customer.setDocument("aula-05-04");

        createOrderUseCase.create(customer);

        Mockito.verify(customerRepository, Mockito.times(1))
                .findByDocument("aula-05-04");

        // Tipo da informação para o captor
        var captor = ArgumentCaptor.forClass(String.class);
        // De onde vem a informação
        Mockito.verify(customerRepository, Mockito.times(1))
                .findByDocument(captor.capture());
        var document = captor.getValue();
        Assertions.assertEquals("aula-05-04", document);
    }

    /* - Teste para garantir que o pedido tenha sido salvo
        -- Dado que possuo um cliente
        -- Quando eu crio um pedido
        -- O pedido deve ser salvo na base de dados (repository)
     */
    @Test
    public void givenCustomerExists_whenICreateNewOrder_thenOrderShouldBeSave() {
        System.out.println("givenCustomerExists_whenICreateNewOrder_thenOrderShouldBeSave");
        Mockito.when(customerRepository.findByDocument("aula-05-04")).thenReturn(new Customer());

        var customer = new Customer();
        customer.setDocument("aula-05-04");

        var order = createOrderUseCase.create(customer);

        /*
         times - que tenha sido chamado exatamente a quantidade de vezes [times(1) = tenha sido chamado exatamente uma vez]
         atLeast - que tenha sido chamado no mínimo a quantidade de vezes [atLeast(1) = tenha sido chamada ao menos um vez]
         */
        Mockito.verify(orderRepository, Mockito.atLeast(1))
                .save(order);
        /*
        Quando não informado a forma de verificação, é aplicado o Mockito.times(1)
        Mockito.verify(orderRepository).save(order);
         */
    }

}
