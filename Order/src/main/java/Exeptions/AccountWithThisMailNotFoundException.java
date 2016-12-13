package Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aismael on 13.12.2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountWithThisMailNotFoundException extends RuntimeException{
    public AccountWithThisMailNotFoundException(String mail) {
        super("Not Account with '" + mail + "'.");
    }

}

