package pl.training.cloud.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.products.model.Product;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    Optional<Product> getById(Long id);
    Optional<Product> getByName(String name);

}
