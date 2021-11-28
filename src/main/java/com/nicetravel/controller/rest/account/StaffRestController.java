package com.nicetravel.controller.rest.account;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/staff")
public class StaffRestController {

    private final AccountService accountService;

    public StaffRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public List<Account> getAllAccount(){
        return accountService.findAllByStaff();
    }
}
