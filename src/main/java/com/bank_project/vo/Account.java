package com.bank_project.vo;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Account {

    private int account_id;
    private String account_number;
    private String account_name;
    private int user_id;
    private BigDecimal balance;
    private LocalDateTime updated_at;

}
