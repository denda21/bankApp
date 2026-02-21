package com.bank_project.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

@ControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    // Login failed
    @ExceptionHandler(LoginException.class)
    public String handleLoginException(LoginException ex, RedirectAttributes redirectAttributes) {
        logger.warn("Login failed: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login";
    }

    // register
    @ExceptionHandler(RegisterException.class)
    public String handleEmailException(RegisterException ex, RedirectAttributes redirectAttributes) {
        logger.warn("Register failed: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/register";
    }

    // Runtime error
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        logger.error("Runtime error: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    // DB
    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(DataAccessException ex, Model model) {
        logger.error("db error", ex);
        model.addAttribute("errorMessage", "error occured");
        return "error";
    }

    // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex, Model model) {
        logger.error("404 error", ex);
        model.addAttribute("errorMessage", "error occured");
        return "error";
    }

    // 500
    @ExceptionHandler(Exception.class)
    public String handleAll(Exception ex, Model model) {
        logger.error("500 error", ex);
        model.addAttribute("errorMessage", "error occured");
        return "error";
    }
}
