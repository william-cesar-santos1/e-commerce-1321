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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CustomerUseCaseImplUnitTest {

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private INotifierUseCase<Customer> notifier;

    @InjectMocks
    private CustomerUseCaseImpl useCase;

    @Test
    public void busca_cliente_por_document_e_cliente_nao_existe_deve_retornar_vazio() {
        Mockito.when(customerRepository.findByDocument(Mockito.any())).thenReturn(null);

        Customer found = useCase.findByDocument("unit-test");
        Assertions.assertNull(found);
    }

}
