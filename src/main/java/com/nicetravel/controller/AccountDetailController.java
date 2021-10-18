package com.nicetravel.controller;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.entity.Account;
//import com.nicetravel.entity.AccountDetail;
import com.nicetravel.repository.AccountRepository;
//import com.nicetravel.service.AccountDetailService;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account-detail")
public class AccountDetailController {

//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    AccountDetailService accountDetailService;
//
//    @Autowired
//    public AccountDetailController(AccountDetailService accountDetailService) {
//        this.accountDetailService = accountDetailService;
//    }

//    @GetMapping
//    public String getAllAccountDetail(Model model){
//        List<AccountDetail> accountDetails = accountDetailService.getAllAccountDetail();
//        model.addAttribute("accountDetails", accountDetails);
//        return "/account/account-detail/index";
//    }

//        Edit
//    @GetMapping("/edit/{username}")
//    public String edit(Model model, @PathVariable("username") String username){
//        Account account = new Account();
//        String username1 = account.getUsername();
//        System.out.println("user " + username);
//        Integer idAccount  = accountRepository.findAccountsByUsername(username);
//        System.out.println("idAccount: " + idAccount);
//
//
//        AccountDetail accountDetail = accountDetailService.findAccountDetailById(idAccount);
//        System.out.println(accountDetail);
//        model.addAttribute("accountDetail", accountDetail);
//        List<AccountDetail> accountDetails =accountDetailService.getAllAccountDetail();
//        model.addAttribute("accountDetails", accountDetails);
//        return "/account/account-detail/info";
//    }

    //    Update
//    @PostMapping("/update")
//    public String updateAccount(@ModelAttribute("account-detail") AccountDetail accountDetail){
//        accountDetailService.updateAccountDetail(accountDetail);
//        return "redirect:/account-detail";
//    }

}
