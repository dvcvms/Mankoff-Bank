package ru.evsmanko.mankoff.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.evsmanko.mankoff.entity.UserEntity;
import ru.evsmanko.mankoff.repository.UserEntityRepository;
import ru.evsmanko.mankoff.repository.UserRepository;
import ru.evsmanko.mankoff.service.BalanceService;
import ru.evsmanko.mankoff.utils.FormattingUtils;

@Controller
@RequiredArgsConstructor
public class AccountController {
    BalanceService balanceService;
    FormattingUtils formattingUtils;
    UserEntityRepository userEntityRepository;

    private final UserRepository userRepository;
    
    @GetMapping("/user")
    public String userInfo(Model model) {
        val users =  userRepository.findAll();
        model.addAttribute("users", users);
        return "userInf";
    }

    @GetMapping("/user/{id}")
    public String userById(@PathVariable long id, Model model) {
        UserEntity userEntity = userEntityRepository.getUserEntityById(id);

        if (userEntity == null)
            return "user_not_found";

        model.addAttribute("userEntity", userEntity);
        return "user";
    }

    @PostMapping("/user")
    public String saveUserById(@RequestBody UserEntity userEntity, Model model) {
        model.addAttribute("userEntity", userEntityRepository.save(userEntity));
        return "user";
    }
    
    @GetMapping("/balans")
    public String balances(Model model)
    {
        model.addAttribute("data", balanceService.usersBalances());
        model.addAttribute("formatter", formattingUtils);
        return "balances";
    }
}
