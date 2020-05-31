package br.com.beyond.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    private Integer originAccountId;
    private Integer destinyAccountId;
    private BigDecimal amouth;

}
