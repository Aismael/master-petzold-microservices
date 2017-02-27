package Billing.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aismael on 29.01.2017.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class payException extends RuntimeException {
    public payException(String name) {
        super("not enough ammount on Account" + name);
    }
}
