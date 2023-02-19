package ru.evsmanko.mankoff.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private BigDecimal balance;
}
