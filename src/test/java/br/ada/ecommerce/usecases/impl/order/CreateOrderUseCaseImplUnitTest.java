package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CreateOrderUseCaseImplUnitTest {

    // Todo teste é constituído de entrada, processamento e saída conhecida.

    // Dado: Que eu não possuo o cliente cadastrado - Entrada
    // Quando: Cadastrar - Processamento
    // Então: Obtenho sucesso - Saída
    public void cadastrarCliente() {
        var useCase = new CreateOrderUseCaseImpl(
                null,
                null
        );

        var customer = new Customer();
        customer.setDocument("00000");
        customer.setName("dummy-value");
        customer.setBirthDate(LocalDate.now().minusYears(50));
        customer.setEmail(List.of("unit@test.com"));
        customer.setTelephone(List.of("+5500000000000"));

        useCase.create(customer);
    }

    public static void main(String[] args) {
        new CreateOrderUseCaseImplUnitTest().cadastrarCliente();
    }

}
