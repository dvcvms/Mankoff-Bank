package ru.evsmanko.mankoff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.evsmanko.mankoff.entity.User;

@Data
@AllArgsConstructor
public class UserWithBalance {
    private User user;
    private double balance;
}
