package br.ada.ecommerce.integration.custom;

import br.ada.ecommerce.config.WiremockExtensionConfig;
import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderItem;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ExtendWith(WiremockExtensionConfig.class)
public class CreateInvoiceOrderImplIntegrationTest {

    private CreateInvoiceOrderImpl service;

    @BeforeEach
    public void setup() {
        var webClient = WebClient.create("http://localhost:8282");

        service = new CreateInvoiceOrderImpl(webClient);
    }

    @Test
    public void pedidoFinalizado_geroNotaFiscal_devoObterSucesso() {
        WireMock.configureFor(8282);
        WireMock.stubFor(
            WireMock.post("/nota-fiscal")
                    .willReturn(
                            WireMock.aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                                    .withBody("{}")
                    )
        );

        var customer = new Customer();
        customer.setDocument("123456");

        var item = new OrderItem();
        item.setAmount(5);
        item.setSaleValue(BigDecimal.TEN);

        var items = new ArrayList<OrderItem>();
        items.add(item);

        var order = new Order();
        order.setCustomer(customer);
        order.setOrderedAt(LocalDateTime.now());
        order.setItems(items);

        service.create(order);
    }

}
