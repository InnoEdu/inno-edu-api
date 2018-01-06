package inno.edu.api.domain.profile.accomplishment.exceptions;

import java.util.UUID;

public class AccomplishmentNotFoundException extends RuntimeException {
    public AccomplishmentNotFoundException(UUID profileId) {
        super("Could not find accomplishment '" + profileId.toString() + "'.");
    }
}