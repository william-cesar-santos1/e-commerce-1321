package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerComponentTest {

    @MockBean
    private ICustomerUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create() {
        var dto = new CustomerDto();

//        controller.create(dto);
    }

}
