package br.ada.ecommerce;

import br.ada.ecommerce.application.usecases.order.IOrderPayUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationIntegrationTest {

    @Autowired
    private IOrderPayUseCase useCase;

    @Test
    public void justRun() {
        Assertions.assertNotNull(useCase);
    }

}
