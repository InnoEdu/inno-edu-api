package inno.edu.api.domain.user.exceptions;

public class InvalidUserNameOrPasswordException extends RuntimeException {
    public InvalidUserNameOrPasswordException() {
        super("Invalid user name or password.");
    }
}