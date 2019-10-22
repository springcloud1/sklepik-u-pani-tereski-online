package pl.training.cloud.products.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.products.dto.PageDto;
import pl.training.cloud.products.dto.ProductDto;
import pl.training.cloud.products.model.Product;
import pl.training.cloud.products.model.Mapper;
import pl.training.cloud.products.model.ResultPage;
import pl.training.cloud.products.service.ProductsService;

import java.net.URI;
import java.util.List;

@RequestMapping("products")
@RestController
@RequiredArgsConstructor
public class ProductsController {

    @NonNull
    private ProductsService productsService;

    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        Product product = dtoToModel(productDto);
        Product productFromDb = productsService.addProduct(product);
        URI uri = uriBuilder.requestUriWithId(productFromDb.getId());
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "{product-id}", method = RequestMethod.GET)
    public ProductDto getProductById(@PathVariable("product-id") Long id) {
        Product product = productsService.getProductById(id);
        return modelToDto(product);
    }

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<ProductDto> getProducts(
            @RequestParam(required = false, defaultValue = "0", name = "pageNumber") int pageNumber,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize) {
        ResultPage<Product> resultPage = productsService.getProducts(pageNumber, pageSize);
        List<ProductDto> productsDtos = mapper.map(resultPage.getContent(), ProductDto.class);
        return new PageDto<>(productsDtos, resultPage.getPageNumber(), resultPage.getTotalPages());
    }

    @RequestMapping(value = "{product-id}", method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@PathVariable("product-id") Long id, @RequestBody ProductDto productDto) {
        Product product = dtoToModel(productDto);
        product.setId(id);
        productsService.updateProduct(product);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "addProductToBasket/{product-id}", method = RequestMethod.POST)
    public ResponseEntity addProductToBasket(@PathVariable("product-id") Long id, @RequestBody ProductDto productDto) {
        Product product = dtoToModel(productDto);
        product.setId(id);
        productsService.addProductToBasket(product);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "{product-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable("product-id") Long id) {
        productsService.deleteProductWithId(id);
        return ResponseEntity.noContent().build();
    }

    private ProductDto modelToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product dtoToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }

}
