package pl.training.cloud.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private Double price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int number;

}
