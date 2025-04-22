package br.ada.ecommerce.application.usecases.repository;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);

}
