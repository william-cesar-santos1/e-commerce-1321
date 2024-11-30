package br.ada.ecommerce.integration.controllers.product;

import br.ada.ecommerce.model.Product;
import br.ada.ecommerce.usecases.product.IProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private IProductUseCase productUseCase;

    public ProductController(IProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody ProductDto dto) {
        Product product = fromDto(dto);
        productUseCase.create(product);
        return toDto(product);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> list() {
        return productUseCase.listAll().stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private Product fromDto(ProductDto dto) {
        Product product = new Product();
        product.setBarcode(dto.getBarcode());
        product.setDescription(dto.getDescription());
        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        return product;
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setBarcode(product.getBarcode());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }

}
