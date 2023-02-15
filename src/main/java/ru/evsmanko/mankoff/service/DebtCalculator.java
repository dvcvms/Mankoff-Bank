package ru.evsmanko.mankoff.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.evsmanko.mankoff.entity.Credit;
import ru.evsmanko.mankoff.entity.Debit;
import ru.evsmanko.mankoff.entity.User;
import ru.evsmanko.mankoff.repository.CreditRepository;
import ru.evsmanko.mankoff.repository.DebitRepository;
import ru.evsmanko.mankoff.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DebtCalculator {
    private final UserRepository userRepository;
    private final DebitRepository debitRepository;
    private final CreditRepository creditRepository;

    @Value("${currency.EUR}")
    private Double currencyUSDtoEUR;
    @Value("${currency.RUB}")
    private Double currencyUSDtoRUB;
    @Value("${app.loan_coefficient}")
    private Double loanCoefficient;
    public double creditCheck(long id, String mode){
        val credits = creditRepository.findAllByUserId(id);
        val creditSum = credits.stream().mapToDouble(Credit::getAmount).sum();
        return switch (mode) {
            case "USD" -> moneyExchangerRUBtoUSD(creditSum);
            case "EUR" -> moneyExchangerRUBtoEUR(creditSum);
            default -> creditSum;
        };
    }
    public double debitCheck(long id, String mode){
        val debits = debitRepository.findAllByUserId(id);
        val debitSum = debits.stream().mapToDouble(Debit::getAmount).sum();
        return switch (mode) {
            case "USD" -> moneyExchangerRUBtoUSD(debitSum);
            case "EUR" -> moneyExchangerRUBtoEUR(debitSum);
            default -> debitSum;
        };
    }
    public double moneyExchangerUSDtoRUB(double amount){
        return amount*currencyUSDtoRUB ;
    }
    public double moneyExchangerRUBtoUSD(double amount){
        return amount/currencyUSDtoRUB ;
    }
    public double moneyExchangerRUBtoEUR(double amount){
        return moneyExchangerRUBtoUSD(amount)/currencyUSDtoEUR ;
    }
    public double moneyExchangerEURtoRUB(double amount){
        return moneyExchangerUSDtoRUB(currencyUSDtoEUR*amount) ;
    }
    public List<User> usersWithCreditMoreThanDebit(){
        val allUsers =  userRepository.findAll();
        return allUsers.stream().filter(user ->
             creditCheck(user.getId(), "RUB")* loanCoefficient > debitCheck(user.getId(), "RUB")
        ).collect(Collectors.toList());
    }
}
