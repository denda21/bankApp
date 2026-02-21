package com.bank_project.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferHistory {

    private int account_id;
    private String transfer_with;
    private BigDecimal amount;
    private String transfer_type; // IN or OUT
    private LocalDateTime transfer_time;

    public TransferHistory(int account_id, String transfer_with, BigDecimal amount, String transfer_type) {
        this.account_id = account_id;
        this.transfer_with = transfer_with;
        this.amount = amount;
        this.transfer_type = transfer_type;
        this.transfer_time = LocalDateTime.now();
    }
}
