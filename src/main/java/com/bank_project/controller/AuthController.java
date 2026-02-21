package com.bank_project.controller;

import com.bank_project.error.RegisterException;
import com.bank_project.vo.User;
import com.bank_project.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) throws LoginException {

        User user = authService.login(email, password);

        session.setAttribute("user_id", user.getUser_id());
        session.setAttribute("user_name", user.getUser_name());
        session.setAttribute("email", user.getEmail());

        return "redirect:/app/dashboard";
    }

    @PostMapping("/register")
    public String register(@RequestParam("user_name") String user_name,
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws RegisterException {

        authService.register(user_name, email, password);

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
