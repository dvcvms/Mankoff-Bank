package ru.evsmanko.mankoff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;
import ru.evsmanko.mankoff.repository.MccRepository;
import ru.evsmanko.mankoff.repository.UserRepository;
import ru.evsmanko.mankoff.service.BalanceService;
import ru.evsmanko.mankoff.service.MccService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mankoff")
@RequiredArgsConstructor
public class BankInfoController {

    private final UserRepository userRepository;
    private final BalanceService balanceService;
    private final MccService mccService;


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
        model.addAttribute("mccCodes", mccService.mccEntities());
        return "mccall";
    }

    @PostMapping("/mcc")
    public String saveMccByCode(@RequestBody MccDto mcc, Model model) {
        mccService.save(mcc);

        return "mccall";
    }

}
