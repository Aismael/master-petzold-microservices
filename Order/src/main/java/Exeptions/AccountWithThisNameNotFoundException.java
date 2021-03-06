package Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Martin Petzold on 13.12.2016.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountWithThisNameNotFoundException extends RuntimeException {
    public AccountWithThisNameNotFoundException(String name) {
        super("Not Account with '" + name + "'.");
    }

}

