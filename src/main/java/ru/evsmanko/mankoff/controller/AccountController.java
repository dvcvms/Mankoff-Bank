package ru.evsmanko.mankoff.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import ru.evsmanko.mankoff.dto.UserEntityDto;
import ru.evsmanko.mankoff.service.UserService;
import ru.evsmanko.mankoff.dto.TransferDto;
import ru.evsmanko.mankoff.dto.PaymentDto;
import org.springframework.web.bind.annotation.PostMapping;
import ru.evsmanko.mankoff.repository.UserRepository;
import ru.evsmanko.mankoff.service.BalanceService;
import ru.evsmanko.mankoff.service.TransferService;
import ru.evsmanko.mankoff.service.PaymentService;
import ru.evsmanko.mankoff.utils.FormattingUtils;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final BalanceService balanceService;
    private final FormattingUtils formattingUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TransferService transferService;
    private final PaymentService paymentService;

    @GetMapping("/user")
    public String userInfo(Model model) {
        val users =  userRepository.findAll();
        model.addAttribute("users", users);
        return "userInf";
    }

    @GetMapping("/transfers")
    public String transferInfo(Model model) {
        val transferEntities = transferService.findAll();
        model.addAttribute("transferEntities", transferEntities);
        return "transfers";
    }

    @GetMapping("/transfers/{id}")
    public String transferInfoBySenderId(@PathVariable Long id, Model model) {
        val transferEntities = transferService.findAllByIdSender(id);
        model.addAttribute("transferEntities", transferEntities);
        return "transfersById";
    }

    @GetMapping("/transfer-create")
    public String createTransferForm(Model model) {
        model.addAttribute("transfer", new TransferDto());
        return "transferCreate";
    }

    @PostMapping(
            path = "/transfer-create",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String transferAdd(TransferDto transfer, Model model) {
        transferService.save(transfer);
        return "redirect:/transfers";
    }

    @GetMapping("/user/{id}")
    public String userById(@PathVariable long id, Model model) {
        Optional<UserEntityDto> user = userService.getById(id);

        if (user.isEmpty())
            return "user/not_found";

        model.addAttribute("user", user.get());
        return "user/data";
    }

    @PostMapping("/user")
    public String saveUserById(@RequestBody UserEntityDto userToSave, Model model) {
        Optional<UserEntityDto> user = userService.save(userToSave);

        if (user.isEmpty())
            return "user/save_error";

        model.addAttribute("user", user.get());
        return "user/data";
    }

    @GetMapping("/balans")
    public String balances(Model model)
    {
        model.addAttribute("data", balanceService.usersBalances());
        model.addAttribute("formatter", formattingUtils);
        return "balances";
    }

    @GetMapping("/payment/new")
    public String newPayment(Model model) {
        model.addAttribute("payment", new PaymentDto());
        return "new";
    }

    @PostMapping("/payment/new")
    public String create(@ModelAttribute("payment") PaymentDto payment) {
        paymentService.save(payment);
        return "new";
    }

    @GetMapping("/user/payments/{userId}")
    public String userPayments(@PathVariable long userId, Model model) {
        model.addAttribute("userPayments", paymentService.findPaymentsByBuyerId(userId));
        return "payments";
    }
}
