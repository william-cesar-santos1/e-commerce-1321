package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

// Exemplo de teste pobre, não cobre muitos cenários
@ExtendWith(MockitoExtension.class)
public class CustomerControllerUnitTest {

    @Mock
    private ICustomerUseCase useCase;

    @InjectMocks
    private CustomerController controller;

    @Test
    public void clienteNaoExiste_realizadoOCadastroSemONome_devoObterFalha() {
        var customerDto = new CustomerDto();
        customerDto.setName(null);
        customerDto.setDocument("00000");
        customerDto.setBirthDate(LocalDate.now());
        customerDto.setEmail(List.of("email@unit.test"));
        customerDto.setTelephone(List.of("0000000"));

        Assertions.assertThrows(
                Exception.class,
                () -> controller.create(customerDto)
        );
    }

}
