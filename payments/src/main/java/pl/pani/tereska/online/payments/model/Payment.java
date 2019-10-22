package pl.pani.tereska.online.payments.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Payment {
    @GeneratedValue
    @Id
    private Long id;
    private Long amount;
    private String name;
    private String surname;
    private String pesel;
}
