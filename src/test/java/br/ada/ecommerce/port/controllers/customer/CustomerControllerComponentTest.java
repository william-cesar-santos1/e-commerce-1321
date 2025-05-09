package br.ada.ecommerce.port.controllers.customer;

import br.ada.ecommerce.application.usecases.customer.ICustomerUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerComponentTest {

    @MockBean // @MockBean é utilizado devido ao uso do SpringBootTest
    private ICustomerUseCase useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCustomerNoHasName_whenITryRegister_thenBadRequest() throws Exception {
        // Given
        var customerDto = new CustomerDto();
        customerDto.setName(null);
        customerDto.setDocument("dummy-value");

        // When
        var request = MockMvcRequestBuilders.post("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                // objectMapper.writeValueAsString == escrever o objeto customerDto como Json
                .content(objectMapper.writeValueAsString(customerDto));
        var response = mockMvc.perform(request);

        // Then
        response.andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }


}
