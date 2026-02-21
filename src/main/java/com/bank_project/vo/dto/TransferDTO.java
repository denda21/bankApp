package com.bank_project.vo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDTO {
    private String sender_account_number;
    private String receiver_account_number;
    private BigDecimal amount;
}
