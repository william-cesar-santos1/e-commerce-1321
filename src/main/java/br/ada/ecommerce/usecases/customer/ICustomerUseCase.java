package br.ada.ecommerce.usecases.customer;

import br.ada.ecommerce.model.Customer;

import java.util.List;

public interface ICustomerUseCase {

    void create(Customer customer);

    List<Customer> list();

    Customer findByDocument(String document);

    List<Customer> findByName(String name);

    void update(Customer customer);

    Customer delete(Long id);

}
