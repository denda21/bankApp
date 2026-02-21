package com.bank_project.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transfer {

    private int transfer_id;
    private int sender_id;
    private int receiver_id;
    private BigDecimal amount;
    private LocalDateTime transfer_time;

}
