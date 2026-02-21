package com.bank_project.controller;

import com.bank_project.vo.dto.TransferDTO;
import com.bank_project.vo.dto.PagedResult;
import com.bank_project.vo.TransferHistory;
import com.bank_project.service.AccountService;
import com.bank_project.service.TransferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/createAccount")
    public String createAccount(@RequestParam("account_name") String account_name,
            HttpSession session) {

        int user_id = (Integer) session.getAttribute("user_id");
        accountService.createAccount(user_id, account_name);

        return "redirect:/app/dashboard";
    }

    @PostMapping("/{accountNumber}/transfer")
    public String transfer(@ModelAttribute TransferDTO transferDTO) throws Exception {

        transferService.sendMoney(transferDTO);

        return "redirect:/app/dashboard";
    }

    @GetMapping("/{accountId}/history")
    public ModelAndView getAccountTransferHistory(@RequestParam("account_number") String account_number,
            @RequestParam(defaultValue = "1") int page) {

        PagedResult<TransferHistory> result = accountService.getAccountHistory(account_number, page);

        ModelAndView mav = new ModelAndView("transferHistory");
        mav.addObject("account_number", account_number);
        mav.addObject("transferHistory", result.getItems());
        mav.addObject("pagingDTO", result.getPagingDTO());

        return mav;
    }

}
