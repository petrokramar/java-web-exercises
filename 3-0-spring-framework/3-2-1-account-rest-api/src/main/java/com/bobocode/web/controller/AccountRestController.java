package com.bobocode.web.controller;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
    private final AccountDao accountDao;

    public AccountRestController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping
    public List<Account>findAll() {
        return accountDao.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return accountDao.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account save(@RequestBody Account account) {
        return accountDao.save(account);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Account update(@RequestBody Account account, @PathVariable Long id) {
        if (!Objects.equals(id, account.getId())) {
            throw new IllegalStateException();
        }
        return accountDao.save(account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountDao.remove(accountDao.findById(id));
    }
}
