package com.bank_project.controller;

import com.bank_project.vo.Account;
import com.bank_project.vo.dto.PagedResult;
import com.bank_project.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session,
            @RequestParam(defaultValue = "1") int page) {
        Integer user_id = (Integer) session.getAttribute("user_id");
        String user_name = (String) session.getAttribute("user_name");

        PagedResult<Account> result = accountService.getUserAccounts(user_id, page);

        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("user_name", user_name);
        mav.addObject("user_accounts", result.getItems());
        mav.addObject("pagingDTO", result.getPagingDTO());

        return mav;
    }

}
