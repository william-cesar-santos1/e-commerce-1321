package br.ada.ecommerce.integration.sms;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.INotifierUseCase;

public class CustomerSmsNotifierImpl implements INotifierUseCase<Customer> {

    private SendSms sendSms;

    public CustomerSmsNotifierImpl(SendSms sendSms) {
        this.sendSms = sendSms;
    }

    @Override
    public void registered(Customer customer) {
        sendSms.send("1111111111111", customer.getTelephone(), "Bem vindo. Click no link abaixo para confirmar seu cadastro.");
    }

    @Override
    public void updated(Customer customer) {
        sendSms.send("1111111111111", customer.getTelephone(), "Suas informações foram atualizadas. Caso tenha sido você mesmo, pode ignorar esse email.");
    }

    @Override
    public void deleted(Customer customer) {
        sendSms.send("1111111111111", customer.getTelephone(), "Sentiremos sua falta e esperamos seu retorno logo.");
    }

}
