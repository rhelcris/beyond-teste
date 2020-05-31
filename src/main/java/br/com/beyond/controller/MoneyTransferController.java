package br.com.beyond.controller;

import br.com.beyond.controller.dto.TransferDTO;
import br.com.beyond.domain.Account;
import br.com.beyond.service.AccountService;
import br.com.beyond.service.MoneyTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/moneytransfer")
public class MoneyTransferController {

    private final MoneyTransfer moneyTransfer;

    private final AccountService accountService;

    @Autowired
    public MoneyTransferController(MoneyTransfer moneyTransfer, AccountService accountService) {
        this.moneyTransfer = moneyTransfer;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> moneyTransfer(@RequestBody TransferDTO dto) {
        boolean transferred = moneyTransfer.transfer(dto.getOriginAccountId(), dto.getDestinyAccountId(), dto.getAmouth());

        return ResponseEntity.ok( "Founds transferred successfully" );
    }

}
