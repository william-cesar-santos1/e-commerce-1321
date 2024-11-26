package br.ada.ecommerce.integration.sms;

import br.ada.ecommerce.config.WiremockExtensionConfig;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@ExtendWith(WiremockExtensionConfig.class)
public class SendSmsIntegrationTest {

    @Inject
    private SendSms sendSms;

    @Test
    public void possuoONumero_envioSms_devoObterSucesso() {
        var request = new SmsRequest();
        request.setContent("Bem vindo");
        request.setPhones(List.of("11111"));

        var result = sendSms.send(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("dummy-value", result.getMessageId());
    }

}
