package br.ada.ecommerce.usecases.impl.customer;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.INotifierUseCase;
import br.ada.ecommerce.usecases.repository.ICustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class CustomerUseCaseImplUnitTest {

    @Mock// Cria um objeto ficticio para testes
    private ICustomerRepository customerRepository;
    @Mock// Cria um objeto ficticio para testes
    private INotifierUseCase<Customer> notifier;

    @InjectMocks// Cria o objeto real e injeta os ficticios que foram criados.
    private CustomerUseCaseImpl useCase;

    @Test
    public void clienteSemId_realizoCadastro_obtenhoSucesso() {
        Mockito.when(customerRepository.save(Mockito.any()))
                .thenAnswer(new Answer<Customer>() {
                    @Override
                    public Customer answer(InvocationOnMock invocation) throws Throwable {
                        Customer customer = invocation.getArgument(0);
                        customer.setId(System.currentTimeMillis());
                        return customer;
                    }
                });

        var customer = new Customer();
        customer.setId(null);

        useCase.create(customer);

        Assertions.assertNotNull(customer.getId());
    }

    // Dado: cliente existe na base de dados
    // Quando: realizo a consulta pelo documento
    // Então: retorno o cliente
    @Test
    public void clienteExistente_buscarPorDocumento_deveRetornarOCliente() {
        // Dado
        var customer = new Customer();
        customer.setDocument("dummy-value");
        Mockito.when(customerRepository.findByDocument("dummy-value"))
                .thenReturn(customer);

        // Quando
        var encontrado = useCase.findByDocument("dummy-value");

        // Então
        Assertions.assertEquals(customer, encontrado);
    }

    // Dado: cliente possui um nome valido
    // Quando: realizo a busca por nome
    // Então: retorna a lista com os clientes
    @Test
    public void clientePossuiNomeValido_realizoABuscaPorNome_retornaALista() {
//        var customer = new Customer();
//        customer.setName("José");
        var customers = IntStream.range(0, 45)
                .mapToObj(index -> {
                    var customer = new Customer();
                    customer.setName("José - " + index);
                    return customer;
                }).toList();
        Mockito.when(customerRepository.findByName("José"))
                .thenReturn(customers);

        var listaDeClientesEncontrados = useCase.findByName("José");

        Assertions.assertEquals(customers, listaDeClientesEncontrados);
    }

    @Test
    public void clienteJaCastrado_atualizoOCliente_deveEnviarNotificacao() {
        var customer = new Customer();
        customer.setId(10l);

        useCase.update(customer);

        Mockito.verify(notifier, Mockito.times(1))
                .updated(customer);
    }

}
