package inno.edu.api.domain.user.root.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("The passwords don't match.");

    }
}
