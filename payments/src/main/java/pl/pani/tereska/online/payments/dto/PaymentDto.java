package pl.pani.tereska.online.payments.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long amount;
    private String name;
    private String surname;
    private String pesel;
}
