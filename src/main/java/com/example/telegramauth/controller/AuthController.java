package com.example.telegramauth.controller;

import com.example.telegramauth.DTO.UserDTO;
import com.example.telegramauth.service.TelegramValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final TelegramValidator validator;
    @Autowired
    public AuthController(TelegramValidator validator) {
        this.validator = validator;
    }

    @GetMapping("/")
    public String showPage(@RequestParam(name = "initData", required = false) String initData, Model model) throws Exception {
        if (initData != null && !initData.isEmpty()) {
            UserDTO userDTO = validator.auth(initData);
            model.addAttribute("user", userDTO);
        }
        return "info";
    }
}
