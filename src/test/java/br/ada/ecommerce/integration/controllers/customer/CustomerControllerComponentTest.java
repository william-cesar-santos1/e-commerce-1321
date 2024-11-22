package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
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
public class CustomerControllerComponentTest{

    @MockBean
    private ICustomerUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void clienteExistente_realizoConsulta_deveRetornarClienteDaBase() throws Exception {
        // Dado
        var customer = new Customer();
        customer.setName("William");
        Mockito.when(useCase.list())
                        .thenReturn(List.of(customer));

        // Quando
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers")
                        .accept(MediaType.APPLICATION_JSON)
        )
        //Então
        .andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void clienteExistente_realizoConsultaPorNome_deveRetornarClienteDaBase() throws Exception {
        // Dado
        var customer = new Customer();
        customer.setName("William");
        Mockito.when(useCase.findByName("William"))
                .thenReturn(List.of(customer));

        // Quando
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/customers?name=William")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //Então
                .andDo(
                        MockMvcResultHandlers.print()
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

}
