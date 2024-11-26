package br.ada.ecommerce.integration.sms;

import br.ada.ecommerce.config.WiremockExtensionConfig;
import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

@SpringBootTest
@ExtendWith(WiremockExtensionConfig.class)
public class SendSmsIntegrationTest {

    @Inject
    private SendSms sendSms;

    @Test
    public void possuoONumero_envioSms_devoObterSucesso() {
        WireMock.configureFor(8282);
        WireMock.stubFor(
                WireMock.post("/sms")
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody("""
                                                {
                                                    "messageId": "some-value",
                                                    "result": "success"
                                                }
                                                """)

                        )
        );

        var request = new SmsRequest();
        request.setContent("Bem vindo");
        request.setPhones(List.of("123456"));

        var result = sendSms.send(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("some-value", result.getMessageId());
    }

}
