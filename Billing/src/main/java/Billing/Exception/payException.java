package Billing.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exeption die Ausgelöst wird wenn Ein Bank Account im Begriff ist überzogen zu werden
 * Created by Martin Petzold on 29.01.2017.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class payException extends RuntimeException {
    public payException(String name) {
        super("not enough ammount on Account" + name);
    }
}
