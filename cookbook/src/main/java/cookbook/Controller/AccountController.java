package cookbook.Controller;

import cookbook.DTOs.AccountInDTO;
import cookbook.DTOs.AccountLIST;
import cookbook.DTOs.AccountOutDTO;
import cookbook.Entities.Account;
import cookbook.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Aismael on 03.04.2017.
 */
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@RequestMapping(value = "account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "one"+ "/{id}")
    public AccountOutDTO showAccountbyId(@PathVariable("id") Long id){
     return new AccountOutDTO(accountRepository.getOne(id));
    }

    @RequestMapping(value = "one", method = RequestMethod.POST)
    public AccountOutDTO makeAccount(@RequestBody AccountInDTO accountInDTO){
        Account account=new Account(accountInDTO.getMail(),accountInDTO.getName(),accountInDTO.getSurename(),18);
        accountRepository.saveAndFlush(account);
        return new AccountOutDTO(account);
    }

    @RequestMapping(value = "all")
    public AccountLIST showAllAccounts(){
        return new AccountLIST(accountRepository.findAll());
    }

}
