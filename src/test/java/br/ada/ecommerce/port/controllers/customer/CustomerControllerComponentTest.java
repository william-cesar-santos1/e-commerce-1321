package br.ada.ecommerce.port.controllers.customer;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.usecases.customer.ICustomerUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerComponentTest {

    @MockBean // @MockBean/@MockitoBean é utilizado devido ao uso do SpringBootTest
    private ICustomerUseCase useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCustomerNoHasName_whenITryRegister_thenBadRequest() throws Exception {
        // req -> servlet -> filter -> interceptors (deu ruim por não ter o nome)
        // intercetors -> advice -> filter -> servlet -> res
        // Given
        var customerDto = new CustomerDto();
        customerDto.setName(null);
        customerDto.setDocument("dummy-value");

        // When
        // objectMapper.writeValueAsString == escrever o objeto customerDto como Json
        var customerAsJson = objectMapper.writeValueAsString(customerDto);
        var request = MockMvcRequestBuilders.post("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerAsJson);
        var response = mockMvc.perform(request);

        // Then
        response.andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].name")
                        .value("must not be null")
        );
    }

    @Test
    public void givenCustomerHasNameAndDocument_whenITryRegister_thenSuccess() throws Exception {
        // req -> servlet -> filter -> interceptors -> controller
        // controller -> intercetors -> filter -> servlet -> res
        // Given
        var customerDto = new CustomerDto();
        customerDto.setName("William");
        customerDto.setDocument("dummy-value");

        // When
        // objectMapper.writeValueAsString == escrever o objeto customerDto como Json
        var customerAsJson = objectMapper.writeValueAsString(customerDto);
        var request = MockMvcRequestBuilders.post("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerAsJson);
        var response = mockMvc.perform(request);

        // Then
        response.andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void givenCustomerExists_whenIFindByName_thenReturnCustomer() throws Exception {
        // Given
        var customerWilliam = new Customer();
        customerWilliam.setName("William");
        Mockito.when(useCase.findByName("William"))
                .thenReturn(List.of(customerWilliam));

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/customers?name=William")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //Then
                .andDo(
                        MockMvcResultHandlers.print()
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("William")
                );
    }

    @Test
    public void givenCustomersExists_whenIList_thenReturnCustomerList() throws Exception {
        var customer = new Customer();
        customer.setId(10l);
        customer.setName("William");

        Mockito.when(useCase.list()).thenReturn(List.of(customer));

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/customers")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //Then
                .andDo(
                        MockMvcResultHandlers.print()
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").value(10)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("William")
                );
    }

}
