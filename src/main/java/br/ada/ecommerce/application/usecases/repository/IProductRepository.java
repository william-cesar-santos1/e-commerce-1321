package br.ada.ecommerce.application.usecases.repository;

import br.ada.ecommerce.application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDescription(String description);

    Product findByBarcode(String barcode);

}
