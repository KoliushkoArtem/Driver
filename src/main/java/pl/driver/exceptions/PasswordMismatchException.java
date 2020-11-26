package pl.driver.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordMismatchException extends AuthenticationException {

    public PasswordMismatchException(String msg) {
        super(msg);
    }
}