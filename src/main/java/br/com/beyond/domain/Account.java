package br.com.beyond.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal amount;

    public boolean hasFunds(BigDecimal valueToTransfer) {
        return amount.compareTo(valueToTransfer) >= 0;
    }

    public void withdrawMoney(BigDecimal value) {
        this.amount = this.amount.subtract(value);
    }

    public void depositMoney(BigDecimal value) {
        this.amount = this.amount.add(value);
    }
}
