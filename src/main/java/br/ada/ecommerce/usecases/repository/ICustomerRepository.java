package br.ada.ecommerce.usecases.repository;

import br.ada.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByDocument(String document);

    List<Customer> findByName(String name);

}
