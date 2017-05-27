package cookbook.Beans;

import cookbook.Entities.Account;
import cookbook.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Aismael on 03.04.2017.
 */
@Component

public class InitDataBean implements CommandLineRunner {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        accountRepository.saveAndFlush(new Account("m@m.de","Max", "Mustermann",32));
        accountRepository.saveAndFlush(new Account("a@b.de","Adam", "Beer",20));
        accountRepository.saveAndFlush(new Account("b@a.de","Benjamin", "Ale",14));
    }
}
