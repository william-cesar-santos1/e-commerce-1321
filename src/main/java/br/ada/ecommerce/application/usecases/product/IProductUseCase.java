package br.ada.ecommerce.application.usecases.product;

import br.ada.ecommerce.application.model.Product;

import java.util.List;

public interface IProductUseCase {

    void create(Product product);

    List<Product> listAll();

    List<Product> findByDescription(String description);

    Product findByBarcode(String barcode);

    void update(Product product);

    Product delete(Long id);

}
