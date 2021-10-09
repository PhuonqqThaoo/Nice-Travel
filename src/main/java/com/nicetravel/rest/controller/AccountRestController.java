package com.nicetravel.rest.controller;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/account")
public class AccountRestController {
    AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @GetMapping
//    public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
//        if(admin.orElse(false)) {
//            return accountService.getAdministrators();
//        }
//        return accountService.getAllAccount();
//    }

    @GetMapping()
    public List<Account> getAllAccount(){
        return accountService.getAllAccount();
    }

    @GetMapping("{id}")
    public Account getOne(@PathVariable("id") Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping()
    public Account create(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping("{id}")
    public Account update(@PathVariable("id") Integer id,@RequestBody Account account) {
        return accountService.updateAccount(account);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        accountService.deleteAccount(id);
    }
}
