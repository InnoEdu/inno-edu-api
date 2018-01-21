package inno.edu.api.domain.user.root.exceptions;

import java.util.UUID;

public class UserIsNotMentorException extends RuntimeException {
    public UserIsNotMentorException(UUID userId) {
        super("User '" + userId.toString() + "' is not a mentor.");
    }
}
