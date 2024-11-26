package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
import br.ada.ecommerce.usecases.repository.ICustomerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassLoaderUtils;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerComponentTest {

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
                        MockMvcRequestBuilders.get("/customers")//Lembre-se de utilizar a uri do seu controller
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
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("William")
                );
    }

    @Test
    public void clienteNaoExiste_realizadoOCadastroSemONome_devoObterFalha() throws Exception {
        var resource = ClassLoaderUtils.getDefaultClassLoader()
                .getResource("customer-without-name.json");
        var customerJson = Files.readString(Paths.get(resource.toURI()));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].name").value("must not be null")
        );
    }

}
