package br.ada.ecommerce.port.sms;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "sms-client",
        url = "${ecommerce.sms-client.url}"
)
public interface SendSms {

    @PostMapping("/sms")
    SmsResult send(
            @RequestBody SmsRequest request
    );

}
