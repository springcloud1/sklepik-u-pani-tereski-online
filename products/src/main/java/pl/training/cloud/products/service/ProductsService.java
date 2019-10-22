package pl.training.cloud.products.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.training.cloud.products.controller.UriBuilder;
import pl.training.cloud.products.model.Mapper;
import pl.training.cloud.products.model.Product;
import pl.training.cloud.products.model.ResultPage;
import pl.training.cloud.products.repository.ProductsRepository;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class ProductsService {

    @NonNull
    private ProductsRepository productsRepository;

    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @NonNull
    private Source source;

    public Product addProduct(Product product) {
        Optional<Product> optProduct = productsRepository.getByName(product.getName());
        if(optProduct.isPresent()){
            Product productFromDb = optProduct.get();
            productFromDb.setNumber(productFromDb.getNumber()+1);
            productsRepository.saveAndFlush(productFromDb);
            return productFromDb;
        } else {
            product.setNumber(1);
            productsRepository.saveAndFlush(product);
            return product;
        }
    }

    public Product getProductById(Long id) {
        return productsRepository.getById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public ResultPage<Product> getProducts(int pageNumber, int pageSize) {
        Page<Product> productPage = productsRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResultPage<>(productPage.getContent(), productPage.getNumber(), productPage.getTotalPages());
    }

    public void updateProduct(Product product) {
        getProductById(product.getId());
        productsRepository.save(product);
    }

    public void addProductToBasket(Product product) {
        getProductById(product.getId());
        product.setNumber(product.getNumber()-1);
        productsRepository.save(product);
        sendNotification(product.getId(), product.getName(), product.getPrice());
    }

    private void sendNotification(Long productId, String productName, Double price) {
        log.info("Sending product details to basket...");
        Message<String> message = MessageBuilder.withPayload(prepareJsonString(productId, productName, price)).build();
        source.output().send(message);
    }

    private String prepareJsonString(Long productId, String productName, Double price) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", productId);
        jsonObject.put("productName", productName);
        jsonObject.put("price", price);
        return jsonObject.toString();
    }

    public void deleteProductWithId(Long id) {
        productsRepository.deleteById(id);
    }

}
