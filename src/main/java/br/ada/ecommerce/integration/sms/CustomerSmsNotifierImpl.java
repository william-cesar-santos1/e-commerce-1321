package br.ada.ecommerce.integration.sms;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.INotifierUseCase;
import org.springframework.stereotype.Service;

@Service
public class CustomerSmsNotifierImpl implements INotifierUseCase<Customer> {

    private SendSms sendSms;

    public CustomerSmsNotifierImpl(SendSms sendSms) {
        this.sendSms = sendSms;
    }

    @Override
    public void registered(Customer customer) {
        var smsRequest = new SmsRequest();
        smsRequest.setContent("Bem vindo. Click no link abaixo para confirmar seu cadastro.");
        smsRequest.setPhones(customer.getTelephone());
        sendSms.send(smsRequest);
    }

    @Override
    public void updated(Customer customer) {
        var smsRequest = new SmsRequest();
        smsRequest.setContent("Suas informações foram atualizadas. Caso tenha sido você mesmo, pode ignorar esse email.");
        smsRequest.setPhones(customer.getTelephone());
        sendSms.send(smsRequest);
    }

    @Override
    public void deleted(Customer customer) {
        var smsRequest = new SmsRequest();
        smsRequest.setContent("Sentiremos sua falta e esperamos seu retorno logo.");
        smsRequest.setPhones(customer.getTelephone());
        sendSms.send(smsRequest);
    }

}
