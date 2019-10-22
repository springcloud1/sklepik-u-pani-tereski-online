package pl.training.cloud.products.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    private int number;
    private Double price;

}
