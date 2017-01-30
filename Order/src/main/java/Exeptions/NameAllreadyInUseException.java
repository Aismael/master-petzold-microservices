package Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aismael on 29.01.2017.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameAllreadyInUseException extends RuntimeException {
    public NameAllreadyInUseException(String name) {
        super("name already in use '" + name + "'.");
    }

}
