package ru.evsmanko.mankoff.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.evsmanko.mankoff.entity.Credit;
import ru.evsmanko.mankoff.entity.Debit;
import ru.evsmanko.mankoff.entity.User;
import ru.evsmanko.mankoff.model.UserWithBalance;
import ru.evsmanko.mankoff.repository.CreditRepository;
import ru.evsmanko.mankoff.repository.DebitRepository;
import ru.evsmanko.mankoff.repository.UserRepository;

import java.util.List;

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

    public double totalBalance() {
        val debits = (List<Debit>) debitRepo.findAll();
        return debits.stream().mapToDouble(Debit::getAmount).sum();
    }

    private double userDebits(User u) {
        return debitRepo.findAllByUserId(u.getId()).stream().mapToDouble(Debit::getAmount).sum();
    }

    private double userCredits(User u) {
        return creditRepo.findAllByUserId(u.getId()).stream().mapToDouble(Credit::getAmount).sum();
    }

    public List<UserWithBalance> usersBalances() {
        return userRepo.findAll()
                .stream()
                .map(u -> new UserWithBalance(u, userDebits(u) - userCredits(u)))
                .toList();
    }

    public List<User> usersWithCreditMoreThanDebit() {
        return userRepo.findAll()
                .stream()
                .filter(u -> userCredits(u) > userDebits(u) * loanCoefficient)
                .toList();
    }
}
