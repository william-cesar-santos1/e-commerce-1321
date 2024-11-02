package br.ada.ecommerce.integration.email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SendEmailIntegrationTest {

    private String from = "unit@test.com";
    private List<String> to = List.of("one@test.com", "two@test.com");
    private String subject = "unit-test";
    private String content = "unit-test";

    @Autowired
    private SendEmail sendEmail;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig()
                    .withUser("green@mail.com", "123456")
            ).withPerMethodLifecycle(false);

    @Test
    @Order(2)
    public void send_email() throws MessagingException {
        sendEmail.send(from, to, subject, content);

        MimeMessage message = greenMail.getReceivedMessages()[0];
        Assertions.assertEquals(from, message.getFrom()[0].toString());
    }

    @Test
    @Order(1)
    public void send_email_deve_respeitar_o_destinatario() throws MessagingException {
        sendEmail.send(from, to, subject, content);

        MimeMessage message = greenMail.getReceivedMessages()[0];
        Assertions.assertEquals(to.get(0), message.getRecipients(Message.RecipientType.TO)[0].toString());
    }

}
