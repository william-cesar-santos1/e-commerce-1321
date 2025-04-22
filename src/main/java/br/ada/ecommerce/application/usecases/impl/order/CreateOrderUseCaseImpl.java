package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Customer;
import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.order.ICreateOrderUseCase;
import br.ada.ecommerce.application.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.application.usecases.repository.IOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CreateOrderUseCaseImpl implements ICreateOrderUseCase {

    private IOrderRepository repository;
    private ICustomerRepository customerRepository;

    public CreateOrderUseCaseImpl(IOrderRepository repository, ICustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Order create(Customer customer) {
        validCustomer(customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>());
        order.setStatus(OrderStatus.OPEN);
        order.setShippingAddress("Minha casa sempre");
        order.setOrderedAt(LocalDateTime.now());
        repository.save(order);
        return order;
    }

    private void validCustomer(Customer customer) {
        Customer found = customerRepository.findByDocument(customer.getDocument());
        if (found == null) {
            throw new IllegalStateException("Cliente não encontrado");
        }
    }

}
