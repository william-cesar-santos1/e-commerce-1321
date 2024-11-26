package br.ada.ecommerce.integration.sms;

import java.util.List;

public class SmsRequest {

    private List<String> phones;
    private String content;

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
