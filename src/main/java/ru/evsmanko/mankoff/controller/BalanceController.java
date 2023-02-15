package ru.evsmanko.mankoff.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evsmanko.mankoff.model.TotalBalance;
import ru.evsmanko.mankoff.entity.User;
import ru.evsmanko.mankoff.service.BalanceService;

import java.util.List;

@RestController
@RequestMapping("balance")
public class BalanceController {

    @Value("${currency.RUB}")
    private double currencyRUB;

    @Value("${currency.EUR}")
    private double currencyEUR;

    private BalanceService balanceService;

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    private double formatBalance(double balance) {
        return Math.round(balance * 100) / 100d;
    }

    @GetMapping("total")
    public TotalBalance total() {
        val totalBalanceRUB = balanceService.totalBalance();
        val totalBalanceUSD = formatBalance(totalBalanceRUB / currencyRUB);
        val totalBalanceEUR = formatBalance(totalBalanceUSD * currencyEUR);
        return new TotalBalance(totalBalanceRUB, totalBalanceUSD, totalBalanceEUR);
    }

    @GetMapping("over-indebted-users")
    public List<User> overIndebtedUsers() {
        return balanceService.usersWithCreditMoreThanDebit();
    }
}
