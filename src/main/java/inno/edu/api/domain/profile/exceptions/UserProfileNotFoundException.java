package inno.edu.api.domain.profile.exceptions;

import java.util.UUID;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(UUID userId) {
        super("Could not find profile for user '" + userId.toString() + "'.");
    }
}