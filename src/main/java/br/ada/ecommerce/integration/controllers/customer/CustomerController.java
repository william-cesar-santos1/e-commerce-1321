package br.ada.ecommerce.integration.controllers.customer;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.usecases.customer.ICustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private ICustomerUseCase customerUseCase;

    public CustomerController(ICustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto dto) {
        Customer customer = fromDto(dto);
        customerUseCase.create(customer);
        return toDto(customer);
    }

    @GetMapping
    public List<CustomerDto> list(@RequestParam(value = "name", required = false) String name) {
        List<Customer> found = null;
        if (name == null) {
            found = customerUseCase.list();
        } else {
            found = customerUseCase.findByName(name);
        }
        return found.stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private Customer fromDto(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setDocument(dto.getDocument());
        customer.setBirthDate(dto.getBirthDate());
        customer.setTelephone(dto.getTelephone());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setDocument(customer.getDocument());
        dto.setBirthDate(customer.getBirthDate());
        dto.setTelephone(customer.getTelephone());
        dto.setEmail(customer.getEmail());
        return dto;
    }
}
