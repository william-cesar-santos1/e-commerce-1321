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
public class CustomerControllerIntegrationTest {

    @MockBean
    private ICustomerUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void nao_deve_ser_possivel_cadastrar_cliente_sem_nome() throws Exception {
        // O teste garante que ao receber um cliente sem a informação de nome a
        // aplicação irá retornar o status code 400
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .content("""
                                {
                                    "document": "0000",
                                    "email" : ["one@teste.com"],
                                    "telephone": ["999999"],
                                    "birthDate": "2020-01-01"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void nao_deve_ser_possivel_cadastrar_cliente_sem_document() throws Exception {
        // O teste garante que ao receber um cliente sem a informação de nome a
        // aplicação irá retornar o status code 400
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .content("""
                                {
                                    "name": "0000",
                                    "email" : ["one@teste.com"],
                                    "telephone": ["999999"],
                                    "birthDate": "2020-01-01"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].document")
                        .value("must not be null")
        );
    }

    @Test
    public void cliente_com_todas_as_informacoes_deve_ser_cadastrado() throws Exception {
        //Mockito.when(useCase.create(Mockito.any())).thenReturn(new Customer());
        // Quando o método é void, a forma de utilização do mockito modifica um pouco
        Mockito.doAnswer(invocationOnMock -> {
            Customer customer = (Customer) invocationOnMock.getArgument(0);
            customer.setId(123l);
            return null;
        }).when(useCase).create(Mockito.any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .content("""
                                {
                                    "name": "Will",
                                    "document":"000"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id")
                        .exists()
        );
    }

    @Test
    public void nao_deve_ser_possivel_cadastrar_cliente_sem_informar_o_nome() throws Exception {
        // O teste garante que ao receber um cliente sem a informação de nome a
        // aplicação irá retornar o status code 400
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .content("""
                                {
                                    "name": "",
                                    "document": "0000",
                                    "email" : ["one@teste.com"],
                                    "telephone": ["999999"],
                                    "birthDate": "2020-01-01"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void deve_listar_cliente_existentes() throws Exception {
        Customer customer = new Customer();
        customer.setId(123l);
        customer.setName("Will");
        customer.setDocument("0000");
        Mockito.when(useCase.list()).thenReturn(List.of(customer));

        // O teste garante que ao receber um cliente sem a informação de nome a
        // aplicação irá retornar o status code 400
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Will")
        );
    }

}
