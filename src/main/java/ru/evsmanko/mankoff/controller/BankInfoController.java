package ru.evsmanko.mankoff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.evsmanko.mankoff.repository.UserRepository;
import ru.evsmanko.mankoff.service.BalanceService;

@Controller
@RequestMapping("/mankoff")
public class BankInfoController {

    private final UserRepository userRepository;
    private final BalanceService balanceService;

    @Autowired
    public BankInfoController(UserRepository userRepository, BalanceService balanceService) {
        this.userRepository = userRepository;
        this.balanceService = balanceService;
    }

    @GetMapping("/contacts")
    public String getContactForm(Model model) {
        model.addAttribute("people", userRepository.findAll());
        return "contacts";
    }


    @GetMapping("/information")
    public String getInfo(Model model) {
        model.addAttribute("balance", balanceService);
        return "information";
    }

    @GetMapping("/contacts/{id}")
    public String showContactInformation(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", userRepository.getUserById(id));
        return "show";
    }
}
