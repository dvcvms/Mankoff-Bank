package ru.evsmanko.mankoff.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.evsmanko.mankoff.converter.UserConvertor;
import ru.evsmanko.mankoff.dto.UserDto;
import ru.evsmanko.mankoff.entity.Credit;
import ru.evsmanko.mankoff.entity.Debit;
import ru.evsmanko.mankoff.entity.User;
import ru.evsmanko.mankoff.repository.CreditRepository;
import ru.evsmanko.mankoff.repository.DebitRepository;
import ru.evsmanko.mankoff.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

@Service
public class BalanceService {

    @Value("${app.loan_coefficient}")
    private double loanCoefficient;

    private UserRepository userRepo;

    private DebitRepository debitRepo;

    private CreditRepository creditRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setDebitRepo(DebitRepository debitRepo) {
        this.debitRepo = debitRepo;
    }

    @Autowired
    public void setCreditRepo(CreditRepository creditRepo) {
        this.creditRepo = creditRepo;
    }

    @Autowired
    private UserConvertor userConvertor;

    public double totalBalance() {
        val debits = (List<Debit>) debitRepo.findAll();
        return debits.stream().mapToDouble(Debit::getAmount).sum();
    }

    private BigDecimal userDebits(User u) {
        return debitRepo.findAllByUserId(u.getId())
                .stream()
                .map(Debit::getAmount)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal userCredits(User u) {
        return creditRepo.findAllByUserId(u.getId())
                .stream()
                .map(Credit::getAmount)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<UserDto> usersBalances() {
        return userRepo.findAll()
                .stream()
                .map(u -> {
                    UserDto dto = userConvertor.convertToDto(u);
                    dto.setBalance(userDebits(u).subtract(userCredits(u)));
                    return dto;
                })
                .toList();
    }

    public List<User> usersWithCreditMoreThanDebit() {
        val lc = BigDecimal.valueOf(loanCoefficient);
        Predicate<User> loanFilter = u -> userCredits(u).compareTo(userCredits(u).multiply(lc)) > 0;
        return userRepo.findAll()
                .stream()
                .filter(loanFilter)
                .toList();
    }
}
