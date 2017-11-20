package inno.edu.api.domain.user.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("The passwords don't match.");

    }
}
