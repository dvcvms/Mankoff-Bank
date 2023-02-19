package ru.evsmanko.mankoff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;
import ru.evsmanko.mankoff.repository.MccRepository;
import ru.evsmanko.mankoff.repository.UserRepository;
import ru.evsmanko.mankoff.service.BalanceService;

import java.util.List;

@Controller
@RequestMapping("/mankoff")
public class BankInfoController {

    private final UserRepository userRepository;
    private BalanceService balanceService;

    @Autowired
    MccRepository mccRep;

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


    @GetMapping("/mcc-all")
    public String allMcc(Model model) {
        List<MCCInfoEntity> mccCodes = mccRep.findAll();
        model.addAttribute("mccCodes", mccCodes);
        return "mccall";
    }

    @PostMapping("/mcc")
    public String saveMccByCode(@RequestBody MCCInfoEntity mcc, Model model) {
        model.addAttribute("mcc", mccRep.save(mcc));
        return "mccall";
    }

}
