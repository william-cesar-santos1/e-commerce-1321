package br.ada.ecommerce.application.usecases.impl.product;

import br.ada.ecommerce.port.controllers.product.ProductAlreadyExists;
import br.ada.ecommerce.application.model.Product;
import br.ada.ecommerce.application.usecases.product.IProductUseCase;
import br.ada.ecommerce.application.usecases.repository.IProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductUseCaseImpl implements IProductUseCase {

    private IProductRepository repository;

    public ProductUseCaseImpl(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void create(Product product) {
        var alreadyExists = repository.findByBarcode(product.getBarcode());
        if (alreadyExists != null) {
            throw new ProductAlreadyExists();
        }
        repository.save(product);
    }

    @Override
    public List<Product> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findByDescription(String description) {
        List<Product> found = new ArrayList<>();
        if (description != null) {
            found = repository.findByDescription(description);
        }
        return found;
    }

    @Override
    public Product findByBarcode(String barcode) {
        Product found = null;
        if (barcode != null) {
            found = repository.findByBarcode(barcode);
        }
        return found;
    }

    @Override
    @Transactional
    public void update(Product product) {
        repository.save(product);
    }

    @Override
    @Transactional
    public Product delete(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            repository.delete(product);
        }
        return product;
    }

}
