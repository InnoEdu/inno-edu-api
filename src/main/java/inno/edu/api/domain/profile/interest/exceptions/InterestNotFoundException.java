package inno.edu.api.domain.profile.interest.exceptions;

import java.util.UUID;

public class InterestNotFoundException extends RuntimeException {
    public InterestNotFoundException(UUID profileId) {
        super("Could not find experience '" + profileId.toString() + "'.");
    }
}